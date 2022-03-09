package Entity;

import Enum.*;
import Observer.Subject;
import Utility.TimestampEvent;

public class Emergency extends Subject {

    // TIPO DI EVENTO GENERATO
    private TypeOfEmergency emergency;
    // TIMESTAMP DI CREAZIONE
    private String timestamp;

    public Emergency(TypeOfEmergency emergency) {
        this.emergency = emergency;
        this.timestamp = TimestampEvent.getTime();
        sendNotify();
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

    public void sendNotify() {
        System.out.println("Attacco Switchboard");
        attach(Switchboard.getInstance());
        System.out.println("Invio Notifica");

        Event event = new Event(this, TypeOfEvents.EMERGENCY);
        setChanged();
        notify(event);
    }

}
