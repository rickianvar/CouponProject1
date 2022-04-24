package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Stack;

public class ConnectionPool {
    // Step 1
    private static ConnectionPool instance = null;
    private Stack<Connection> connections = new Stack<>();
    private static final int SIZE = 10;

    // Step 2

    private ConnectionPool() throws SQLException {
        openAllConnections();
    }


    // Step 3
    public static ConnectionPool getInstance() throws SQLException {
        if (instance == null) {
            synchronized (ConnectionPool.class) {
                if (instance == null) {
                    instance = new ConnectionPool();
                }
            }
        }
        return instance;
    }

    public void openAllConnections() throws SQLException {
        for (int i = 0; i < SIZE; i++) {
            connections.push(DriverManager.getConnection(JDBCUtils.URL, JDBCUtils.USER, JDBCUtils.PASSWORD));
        }
    }

    public void closeAllConnections() throws SQLException {
        if (connections.size() == SIZE) {
            for (Connection conn : connections) {
                conn.close();
            }
        }
    }

    public Connection getConnection() throws InterruptedException {

        if (connections.empty()) {
            synchronized (connections) {
                connections.wait();
            }
        }
        return connections.pop();
    }

    public void restoreConnection(Connection conn) {
        synchronized (connections) {
            connections.push(conn);
            connections.notify();
        }

    }

}
