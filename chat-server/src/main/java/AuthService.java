import java.sql.SQLException;

public interface AuthService {
    void start();

    void changeNick(String newNick, String oldNick) throws SQLException;

    String getNickByLoginPass(String login, String pass) throws SQLException;
    void stop();
}
