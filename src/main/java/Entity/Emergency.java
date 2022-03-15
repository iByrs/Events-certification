package Entity;

import Enum.*;
import Observer.Subject;
import Utility.Logger;
import Utility.TimestampEvent;
import com.mysql.cj.log.Log;

public class Emergency extends Subject {

    // TIPO DI EVENTO GENERATO
    private TypeOfEmergency emergency;
    // TIMESTAMP DI CREAZIONE
    private String timestamp;

    public Emergency(TypeOfEmergency emergency) {
        Logger.out(false, "New emergency created. Notify the switchboard. Emergency ->" + emergency);
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
        attach(Switchboard.getInstance());
        Event event = new Event(emergency, TypeOfEvents.EMERGENCY);
        notify(event);
    }

}
