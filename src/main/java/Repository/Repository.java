package Repository;

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
            System.out.println("Trying to connect to database on"+ DB_URL +".");
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PSW);
            System.out.println("Connection to database successfully.");
            query = connection.createStatement();
            query.setQueryTimeout(30);
            System.out.println("Setted query timeout 30 second.");
            return true;
        } catch (SQLException error) {
            System.out.println("Trying to connect to database failure.");
            System.err.println(error.getMessage());
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
