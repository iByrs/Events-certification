package Entity;

import Enum.KindOfEmergency;
import Utility.TimestampEvent;

public class Emergency {

    // TIPO DI EVENTO GENERATO
    private KindOfEmergency emergency;
    // TIMESTAMP DI CREAZIONE
    private String timestamp;

    public Emergency(KindOfEmergency emergency) {
        this.emergency = emergency;
        this.timestamp = TimestampEvent.getTime();
    }

    public KindOfEmergency getEmergency() {
        return emergency;
    }

    public void setEmergency(KindOfEmergency emergency) {
        this.emergency = emergency;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
