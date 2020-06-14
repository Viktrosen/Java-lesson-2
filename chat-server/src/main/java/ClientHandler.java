import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ClientHandler<concurrentDeque> {
    private MyServer myServer;
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private String name;
    private Iterator iterator;

    public String getName() {
        return name;
    }

    public ClientHandler(MyServer myServer, Socket socket) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        try {
            this.iterator = myServer.cld.iterator();
            this.myServer = myServer;
            this.socket = socket;
            this.in = new DataInputStream(socket.getInputStream());
            this.out = new DataOutputStream(socket.getOutputStream());
            this.name = "";
            executorService.execute(() -> {
                try {
                    authentication();
                    broadCastHistory();
                    readMessages();
                } catch (IOException | SQLException e) {
                    e.printStackTrace();
                } finally {
                    closeConnection();
                    executorService.shutdown();
                }
            });
        } catch (IOException e) {
            throw new RuntimeException("Проблемы при создании обработчика клиента");
        }
    }

    public void authentication() throws IOException, SQLException {
        while (true) {
            String str = in.readUTF();
            if (str.startsWith("/auth")) {
                String[] parts = str.split("\\s");
                String nick = myServer.getAuthService().getNickByLoginPass(parts[1], parts[2]);
                if (nick != null) {
                    if (!myServer.isNickBusy(nick)) {
                        sendMsg("/authok " + nick);
                        name = nick;
                        myServer.broadcastMsg(name + " зашел в чат");
                        myServer.subscribe(this);
                        return;
                    } else {
                        sendMsg("Учетная запись уже используется");
                    }
                } else {
                    sendMsg("Неверные логин/пароль");
                }
            }
        }
    }

    public void broadCastHistory(){
        while (iterator.hasNext()){
        sendMsg(iterator.next().toString());}
    }



    public void readMessages() throws IOException, SQLException {
        while (true) {
            String strFromClient = in.readUTF();
            MyServer.LOGGER.info("от " + name + ": " + strFromClient);
            if (strFromClient.equals("/end")) {
                return;
            }
            myServer.broadcastMsg(name + ": " + strFromClient);
            if(strFromClient.startsWith("/w")){
                String[] parts = strFromClient.split("\\s");
                myServer.sendMsgToClient(this, parts[1], strFromClient);
            }

            if (strFromClient.startsWith("/changenick")){
                String[] parts = strFromClient.split("\\s");
                if (!myServer.isNickBusy(parts[1])){
                myServer.getAuthService().changeNick(parts[1],this.name);
                this.name = parts[1];
                    myServer.sendMsgFromSystem(this, "Ник успешно изменён. Теперь вы: "+this.name);}else {
                    myServer.sendMsgFromSystem(this, "Ник уже занят, попробуйте другой");
                }

            }
        }
    }

    public void sendMsg(String msg) {
        try {
            out.writeUTF(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void closeConnection() {
        myServer.unsubscribe(this);
        myServer.broadcastMsg(name + " вышел из чата");
        try {
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}