package com.roy.handlers;

import java.sql.*;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class ConnectionPool {

    private static int MAX_CONNECTIONS=10;
    private static ConnectionPool instance = new ConnectionPool();
    private Set<Connection> connections;
    private static final String CONNECTION_URL="jdbc:mysql://localhost:3306/coupon_system?useSSL=false";
    private static final String USER="root";
    private static final String PASSWORD="roymika91";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private ConnectionPool(){
        connections = new HashSet<>(10);
        try{
            for (int i=0; i<MAX_CONNECTIONS; i++){
                connections.add(generateConnection());
            }
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public static ConnectionPool getInstance(){
        return instance;
    }

    public synchronized Connection getConnection() {
        while (connections.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                return null;
            }
        }
        Iterator<Connection> cursor = connections.iterator();
        Connection connection = cursor.next();
        cursor.remove();
        return connection;
    }

    private static Connection generateConnection() throws SQLException {
        return DriverManager.getConnection(CONNECTION_URL, USER, PASSWORD);
    }

    public synchronized void restoreConnection(Connection connection) {
        connections.add(connection);
        notifyAll();
    }

    public synchronized void closeAllConnections() {
        int closedConnectionsCounter = 0;
        while (closedConnectionsCounter < MAX_CONNECTIONS) {
            while (connections.isEmpty()) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Iterator<Connection> cursor = connections.iterator();
            while (cursor.hasNext()) {
                Connection currentConnection = cursor.next();
                try {
                    currentConnection.close();
                    cursor.remove();
                    closedConnectionsCounter++;
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public static void closeResources(PreparedStatement preparedStatement) {
        try {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void closeResources(PreparedStatement preparedStatement, ResultSet resultSet) {
        closeResources(preparedStatement);
        try {
            if (resultSet != null) {
                resultSet.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}