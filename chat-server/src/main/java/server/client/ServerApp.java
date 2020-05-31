package server.client;

import server.client.MyServer;

import java.sql.SQLException;

public class ServerApp {

    public static void main(String[] args) throws SQLException {
        new MyServer();
    }
}
