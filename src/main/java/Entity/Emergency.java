package Entity;

import Enum.TypeOfEmergency;
import Utility.TimestampEvent;

public class Emergency {

    // TIPO DI EVENTO GENERATO
    private TypeOfEmergency emergency;
    // TIMESTAMP DI CREAZIONE
    private String timestamp;

    public Emergency(TypeOfEmergency emergency) {
        this.emergency = emergency;
        this.timestamp = TimestampEvent.getTime();
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
}
