import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedDeque;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class MyServer {
    private final int PORT = 8189;


    private List<ClientHandler> clients;
    private AuthService authService;
    ConcurrentLinkedDeque<String> cld;
    public int quantityOfMsg = 0;
    static final Logger LOGGER = LogManager.getLogger(MyServer.class);

    public AuthService getAuthService() {
        return authService;
    }

    public MyServer() {

        try (ServerSocket server = new ServerSocket(PORT)) {
            authService = new BaseAuthService();
            authService.start();
            clients = new ArrayList<>();
            cld = new ConcurrentLinkedDeque<>();
            while (true) {
                LOGGER.info("Сервер ожидает подключения");
                Socket socket = server.accept();
                LOGGER.info("Клиент подключился");
                new ClientHandler(this, socket);
            }
        } catch (IOException | SQLException e) {
            LOGGER.error("Ошибка в работе сервера");
        } finally {
            if (authService != null) {
                authService.stop();
            }
        }
    }

    public synchronized void sendMsgToClient(ClientHandler from, String nickTo, String msg) {
        for (ClientHandler o : clients) {
            if (o.getName().equals(nickTo)) {
                o.sendMsg("от " + from.getName() + ": " + msg);
                from.sendMsg("клиенту " + nickTo + ": " + msg);
                return;
            }
        }
        from.sendMsg("Участника с ником " + nickTo + " нет в чат-комнате");
    }

    public synchronized void sendMsgFromSystem(ClientHandler to, String msg){
        for (ClientHandler o : clients) {
            if (o.getName().equals(to.getName())){
                o.sendMsg("System: "+msg);
                return;
            }
        }
    }

    public synchronized void broadcastClientsList() {
        StringBuilder sb = new StringBuilder("/clients ");
        for (ClientHandler o : clients) {
            sb.append(o.getName() + " ");
        }
        broadcastMsg(sb.toString());
    }

    public synchronized void unsubscribe(ClientHandler o) {
        clients.remove(o);
        broadcastClientsList();
    }

    public synchronized void subscribe(ClientHandler o) {
        clients.add(o);
        broadcastClientsList();
    }

    public synchronized boolean isNickBusy(String nick) {
        for (ClientHandler o : clients) {
            if (o.getName().equals(nick)) {
                return true;
            }
        }
        return false;
    }

    public synchronized void broadcastMsg(String msg) {
        for (ClientHandler o : clients) {
            o.sendMsg(msg);
        }
        quantityOfMsg++;
        if (!cld.contains(msg)){
        cld.add(msg);
        if (cld.size()==100){cld.pollFirst();cld.addLast(msg);}}
    }
}