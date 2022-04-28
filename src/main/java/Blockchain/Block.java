package Blockchain;
import Entity.*;
import Utility.TimestampEvent;

public class Block {

    public String hash;
    public String previousHash;
    private String timestamp_creazione_blocco;
    private String timestamp_creazione_evento;
    private Event event;
    private int nonce;

    public Block(Event event, String previousHash) {
        this.previousHash = previousHash;
        this.timestamp_creazione_evento = event.getTimestamp();
        this.timestamp_creazione_blocco = TimestampEvent.getTime();
        this.event = event;
        this.hash = hashing();
    }

    public String hashing() {
        String calculatedHash = Hash.hashingSha256(
                previousHash +
                        timestamp_creazione_evento +
                        timestamp_creazione_evento +
                        nonce +
                        event.getEvent().toString()
        );
        return calculatedHash;
    }

    public void mining(int id, int difficulty) {
        String target = new String(new char[difficulty]).replace('\0', '0');
        nonce = id;
        while(!hash.substring(0, difficulty).equals(target)) {
            nonce++;
            hash = hashing();
        }
    }

    public Event getEvent() {
        return event;
    }

    public String getTimestamp_creazione_blocco() {
        return timestamp_creazione_blocco;
    }

    public String getTimestamp_creazione_evento() {
        return timestamp_creazione_evento;
    }

    public int getNonce() {
        return nonce;
    }
}
