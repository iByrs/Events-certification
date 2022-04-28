package Controller;

import Blockchain.Block;
import Blockchain.Blockchain;
import Entity.Center;
import Entity.Event;
import Observer.*;
import Enum.*;
import Utility.Logger;
import Utility.TimestampEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class BlockController extends Subject implements Observer {

    private static BlockController obj;
    private CompletableFuture[] threads;
    private List<Event> queue;
    private final int slots = 1;

    private BlockController() {
        queue = new ArrayList<>();
        threads = new CompletableFuture[slots];
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
        Logger.out(true,"BLOCKCONTROLLER: Nuovo evento ricevuto, lo metto in coda");
        queue.add(event);
        if(queue.size() == slots) {
            extractEvents();
        }
    }

    @Override
    public void update(int id, Event event) throws InterruptedException {
    }

    private void extractEvents() {
        Logger.out(true,"BLOCKCONTROLLER: Coda piena, estraggo gli eventi. Avvio la procedura di mining degli eventi per la certificazione");
        Event[] events = new Event[slots];
        events = queue.toArray(events);
        queue = new ArrayList<>();
        String logs = new String();
        for(Event event : events) {
            logs += event.getEvent() + "\n";
        }
        Event eventiDaCertificare = new Event(logs, TypeOfEvents.BLOCKCHAIN);
        startMining(eventiDaCertificare);
    }

    private void startMining(Event event) {
        for(int i = 0; i < slots; i++) {
            int thread_id = i;
            threads[i] = CompletableFuture.supplyAsync(() -> createNewBlock(event, thread_id));
        }
        try {
            Block block = (Block) CompletableFuture.anyOf(threads).get();
            Logger.out(true,"BLOCKCONTROLLER: Blocco creato con successo, hash: " + block.hash+" invio alla blockchain");
            Event insertBlock = new Event(block, TypeOfEvents.BLOCKCHAIN);
            notify(insertBlock);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static Block createNewBlock(Event event, int id) {
        Block newBlock = new Block(event, Blockchain.getPreviusHash());
        newBlock.mining(id, Blockchain.difficulty);
        return newBlock;
    }



}
