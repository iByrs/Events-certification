package Repository;

import Utility.Logger;

import java.sql.*;

public class Repository {

    private final String DB_URL = "jdbc:mysql://localhost:3306/Repository";
    private final String DB_USER = "root";
    private final String DB_PSW = "root";
    private Connection connection;
    private Statement query;
    private static Repository obj;

    private Repository() {
        connection = null;
        query = null;
        databaseStartConnection();
    }

    public static Repository getInstance() {
        if (obj == null) {
            obj = new Repository();
        }
        return obj;
    }

    public Statement getStatementQuery() {
        return query;
    }

    private boolean databaseStartConnection() {
        try {
            Logger.out(true,"Trying to connect to database on"+ DB_URL +".");
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PSW);
            Logger.out(true, "Connection to database successfully.");
            query = connection.createStatement();
            query.setQueryTimeout(30);
            Logger.out(true, "Setted query timeout 30 second.");
            return true;
        } catch (SQLException error) {
            Logger.out(true, "Trying to connect to database failure.");
            Logger.out(true, error.getMessage());
        }
        return false;
    }

    public boolean databaseEndConnection() {
        try {
            if(connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return false;
    }

    public boolean addNewRecord(String q) {
        try {
            query.executeUpdate(q);
            return true;
        } catch (SQLException error) {
            System.err.println(error.getMessage());
        }
        return false;
    }

}
