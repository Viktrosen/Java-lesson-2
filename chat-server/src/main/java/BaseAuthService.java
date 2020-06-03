import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BaseAuthService implements AuthService {
    Connection conn;
    Statement statement;
    ResultSet resultSet;




    @Override
    public void start() {
        System.out.println("Сервис аутентификации запущен");
    }

    @Override
    public void stop() {
        System.out.println("Сервис аутентификации остановлен");
    }


    public BaseAuthService() throws SQLException {
        this.conn = DriverManager.getConnection("jdbc:mysql://localhost/chat?useUnicode=true&serverTimezone=UTC","root","");
        this.statement = conn.createStatement();
    }
    @Override
    public void changeNick(String newNick, String oldNick) throws SQLException {
        String query = "UPDATE users SET nickname = '"+newNick+"' WHERE nickname = '"+oldNick+"';";
        statement.executeUpdate(query);
    }


    @Override
    public String getNickByLoginPass(String login, String pass) throws SQLException {
        String query = "SELECT nickname FROM users WHERE login = '"+login+"' && password = '"+pass+"';";
        resultSet = statement.executeQuery(query);
        resultSet.next();
        return resultSet.getString(1);
    }
}
