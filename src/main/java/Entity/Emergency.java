package Entity;

import Enum.*;
import Observer.Subject;
import Utility.Logger;
import Utility.TimestampEvent;

public class Emergency extends Subject{

    // TIPO DI EVENTO GENERATO
    private TypeOfEmergency emergency;
    // TIMESTAMP DI CREAZIONE
    private String timestamp;
    private String description;
    private int id;

    public Emergency(int id, TypeOfEmergency emergency) {
        this.id = id;
        this.emergency = emergency;
        this.timestamp = TimestampEvent.getTime();
        Logger.out(true, "Emergenza " + id + ": Nuova emergenza apparsa. Emergenza " + emergency);
        sendNotify();
    }

    public void sendNotify() {
        attach(Switchboard.getInstance());
        Event event = new Event(this, TypeOfEvents.EMERGENCY);
        Logger.out(true,  "Emergenza " + id + ": Avverto il centralino");
        notify(event);
    }

    public TypeOfEmergency getEmergency() {
        return emergency;
    }

    public void setEmergency(TypeOfEmergency emergency) {
        this.emergency = emergency;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
    public int getId() {
        return id;
    }
}
