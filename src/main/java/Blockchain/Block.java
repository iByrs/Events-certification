package Blockchain;
import Entity.*;
import Enum.*;
import Utility.TimestampEvent;

import java.util.Date;

public class Block {

    public String hash;
    public String previousHash;
    private String timestamp_creazione_blocco;
    private String timestamp_creazione_evento;
    private Event event;
    private int nonce; // IMPOSTATA TUTTI I  VALORI POSSIBILI;
    // MI FERMO QUANDO: INSERIAMO VALORI IN SEQUENZA, MI FERMO QUANDO L' HASH GENERATO INIZIA CON ZERO

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
                        event.getEvent()
        );
        return calculatedHash;
    }

    public void mining(int difficulty) {
        String target = new String(new char[difficulty]).replace('\0', '0');

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
