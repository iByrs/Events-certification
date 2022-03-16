package Repository;

import Blockchain.Block;
import Entity.Event;

public class DbWriter {

    public static void writeOnBlockchain(Block block) {
        String query = "INSERT INTO Blockchain VALUES('"+block.hash+"','"+block.previousHash+"','"+block.getTimestamp_creazione_blocco()+"','"+block.getTimestamp_creazione_evento()+"')";
        Repository.getInstance().addNewRecord(query);
    }
    public static void writeEvent(Event event) {
        String query = "INSERT INTO Events VALUES('')";
    }

}
