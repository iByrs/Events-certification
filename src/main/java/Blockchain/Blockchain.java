package Blockchain;

import java.util.ArrayList;
import Entity.*;
import Enum.*;
import Observer.Observer;
import Repository.Repository;
import Utility.Logger;

public class Blockchain implements Observer {

    private static ArrayList<Block> blockchain = new ArrayList<Block>();
    public static int difficulty = 4;
    private static Blockchain obj;

    private Blockchain() {
        Event firstEvent = new Event("First", TypeOfEvents.BLOCKCHAIN);
        Block genesis = new Block(firstEvent, "Genesis");
        blockchain.add(genesis);
    }

    public static Blockchain getInstance() {
        if(obj == null) {
            obj = new Blockchain();
        }
        return obj;
    }

    public static String getPreviusHash() {
        return blockchain.get(blockchain.size()-1).hash;
    }

    public void addNewBlock(Block block) {
        blockchain.add(block);
        Repository.getInstance().insertNewBlock(block);
    }

    @Override
    public void update(Event event) {
        if(event.getTypeOfEvent() != TypeOfEvents.BLOCKCHAIN) {
            return;
        }
        Block block = (Block) event.getMessage();
        Logger.out(true, "BLOCKCHAIN: Blocco "+ block.hash + " inserimento avvenuto con successo");
        addNewBlock(block);
    }

    @Override
    public void update(int id, Event event) throws InterruptedException {
    }
}