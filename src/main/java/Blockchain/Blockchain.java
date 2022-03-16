package Blockchain;

import java.util.ArrayList;
import Entity.*;
import Enum.*;
public class Blockchain {

    private static ArrayList<Block> blockchain = new ArrayList<Block>();
    public static int difficulty = 4;
    private static Blockchain obj;

    private Blockchain() {
        Event firstEvent = new Event("", TypeOfEvents.BLOCKCHAIN);
        Block genesis = new Block(firstEvent, "");
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
    }

    
}