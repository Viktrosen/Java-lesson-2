package server.auth;

import com.google.common.collect.Lists;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.sql.*;

public class Auth implements AuthService {
    Connection conn;
    Statement statement;
    ResultSet resultSet;

    private static class Entry {
        private String login;
        private String password;
        private String nick;
        Connection conn;
        Statement statement;
        ResultSet resultSet;

        public Entry(String login, String password, String nick) {
            this.login = login;
            this.password = password;
            this.nick = nick;
        }
    }

    private final List<Entry> entries = Lists.newArrayList(
            new Entry("User1", "User1", "User1"),
            new Entry("User2", "User2", "User2"),
            new Entry("User3", "User3", "User3"),
            new Entry("User4", "User4", "User4")
    );




    @Override
    public void start() {
        System.out.println("start auth");
    }

    @Override
    public void stop() {
        System.out.println("stop auth");
    }

    public Auth() throws SQLException {
        this.conn = DriverManager.getConnection("jdbc:mysql://localhost/chat?useUnicode=true&serverTimezone=UTC","root","");
        this.statement = conn.createStatement();
    }

    @Override
    public String getNickByLoginPass(String login, String pass) throws SQLException {
        ResultSet resultSet = statement.executeQuery("SELECT nickname FROM users WHERE login = "+"'"+login+"'"+"&& password = "+"'"+pass+"';");
        return resultSet.getString(1);
    }
}
