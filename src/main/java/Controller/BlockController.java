package Controller;

import Blockchain.Block;
import Blockchain.Blockchain;
import Entity.Center;
import Entity.Event;
import Observer.*;
import Enum.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class BlockController extends Subject implements Observer {

    private static BlockController obj;
    private CompletableFuture[] blocks;
    private List<Event> queue;
    private int n;
    private int m;

    private BlockController() {
        queue = new ArrayList<>();
        this.n = 0;
        this.m = 5;
        blocks = new CompletableFuture[5];
        attach(Center.getInstance());
    }

    public static BlockController getInstance() {
        if(obj == null) {
            obj = new BlockController();
        }
        return obj;
    }


    @Override
    public void update(Event event) {
        if(event.getTypeOfEvent() != TypeOfEvents.CREATE_BLOCK) {
            return;
        }
        if(blocks.length < m) {
            addBlock(event);
        }else {
            addQueue(event);
            startMining();
            popEventInBlocks();
        }
    }

    // CREA IL BLOCCO
    public static Block createNewBlock(Event event) {
        Block newBlock = new Block(event, Blockchain.getPreviusHash());
        newBlock.mining(Blockchain.difficulty);
        return newBlock;
    }

    // UTILIZZATO NEL COMPLETABLEFUTURE
    public Block getBlock() {
        try {
            Block newBlock = (Block) CompletableFuture.anyOf(blocks).get();
            removeCompletableFuture();
            return newBlock;
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }

    // RIMOVIAMO IL COMPLETABLEFUTURE COMPLETATO
    public void removeCompletableFuture() {
        for(int i = 0; i < n; i++) {
            System.out.println(blocks[i].isCancelled());
            if(blocks[i].isDone()) {
                blocks[i] = blocks[n-1];
            }
        }
        blocks[n-1] = null;
        n--;
    }

    // AVVIA L'OPERAZIONE DI MINING SUI BLOCCHI, NOTIFICA
    private void startMining() {
        if(n == m) {
            Block block = getBlock();
            System.out.println(block.hash);
            //removeCompletableFuture();
            Event newEvent = new Event(block, TypeOfEvents.BLOCKCHAIN);
            notify(newEvent);
        }
    }


    private void popEventInBlocks() {
        if(queue.size() > 0)    addBlock(queue.remove(queue.size()-1));
    }

    // AGGIUNGE NELLA CODA
    private void addQueue(Event event) {
        queue.add(event);
    }

    // AGGIUNGE NELL'ARRAY
    public void addBlock(Event event) {
        if(n < m) {
            blocks[n++] = CompletableFuture.supplyAsync(() -> createNewBlock(event));
            //System.out.println(blocks[n-1].isDone());
        }
    }

    public int getN() {
        return blocks.length;
    }

    @Override
    public void update(int id, Event event) throws InterruptedException {
        return;
    }
}
