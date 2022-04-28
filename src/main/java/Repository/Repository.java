package Repository;

import Blockchain.Block;
import Entity.Event;
import Entity.Mission;
import Utility.Logger;
import Utility.TimestampEvent;

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
        createTables();
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
            Logger.out(false,"REPOSITORY: Trying to connect to database on"+ DB_URL);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PSW);
            Logger.out(false, "REPOSITORY: Connection to database successfully");
            query = connection.createStatement();
            query.setQueryTimeout(30);
            Logger.out(false, "REPOSITORY: Setted query timeout 30 second");
            return true;
        } catch (SQLException error) {
            Logger.out(true, "REPOSITORY: Trying to connect to database failure");
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

    public boolean insertNewRecord(String q) {
        try {
            query.executeUpdate(q);
            return true;
        } catch (SQLException error) {
            System.err.println(error.getMessage());
        }
        return false;
    }


    private void createTables() {
        String drop = "DROP TABLE IF EXISTS Center";
        String statement = "CREATE TABLE Center(ID MEDIUMINT NOT NULL AUTO_INCREMENT PRIMARY KEY, Timestamp VARCHAR(255) NOT NULL, Evento VARCHAR(255) NOT NULL, Messaggio VARCHAR(255) NOT NULL)";
        String dropBlockchain = "DROP TABLE IF EXISTS Blockchain";
        String blockchainTable = "CREATE TABLE Blockchain(Hash VARCHAR(255), PreviousHash VARCHAR(255), Timestamp VARCHAR(60), Data VARCHAR(5000))";
        String dropMission = "DROP TABLE IF EXISTS Mission";
        String missionTable = "CREATE TABLE Mission(ID_Missione MEDIUMINT NOT NULL PRIMARY KEY, ID_Team MEDIUMINT NOT NULL, ID_Emergenza MEDIUMINT NOT NULL, Timestamp VARCHAR(2255) )";
        String dropTeam = "DROP TABLE IF EXISTS Team";
        String teamTable = "CREATE TABLE Team(ID_Team MEDIUMINT NOT NULL PRIMARY KEY, ID_MISSION MEDIUMINT NOT NULL, id_e1 VARCHAR(20), id_e2 VARCHAR(20), id_e3 VARCHAR(20))";
        insertNewRecord(drop);
        insertNewRecord(statement);
        Logger.out(false, "REPOSITORY: Table center creation done");
        insertNewRecord(dropBlockchain);
        insertNewRecord(blockchainTable);
        Logger.out(false, "REPOSITORY: Blokchain table creation done");
        insertNewRecord(dropTeam);
        insertNewRecord(teamTable);
        Logger.out(false, "REPOSITORY: Team table creation done");
        insertNewRecord(dropMission);
        insertNewRecord(missionTable);
        Logger.out(false, "REPOSITORY: Mission table creation done");
        Logger.out(false, "REPOSITORY: Tables creation done");
    }

    public void insertNewMission(Mission m) {
        String query = "INSERT INTO Mission (ID_Missione, ID_Team, ID_Emergenza, Timestamp)VALUES ('"+m.getMissionId()+"','"+m.getTeam()+"','"+m.getEmergency()+"','"+ TimestampEvent.getTime() +"')";
        insertNewRecord(query);
    }
    public void insertNewEvent(Event e) {
        String query = "INSERT INTO Center (Timestamp, Evento, Messaggio) VALUES ('"+e.getTimestamp()+"','"+e.getTypeOfEvent()+"','"+e.getMessage().toString()+"')";
        //insertNewRecord(query);
    }
    public void insertNewBlock(Block block) {
        String query = "INSERT INTO Blockchain (Hash, PreviousHash, Timestamp, Data)VALUES ('"+block.hash+"','"+block.previousHash+"','"+block.getTimestamp_creazione_evento()+"','"+block.getEvent().getMessage().toString() +"')";
        insertNewRecord(query);
    }
}
