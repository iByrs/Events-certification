package Entity;

import Utility.TimestampEvent;
import Enum.*;

public class Event {
    private Object message;
    private String timestamp;
    private TypeOfEvents type;
    public Event(Object message, TypeOfEvents type) {
        this.message = message;
        this.type = type;
        this.timestamp = TimestampEvent.getTime();
    }

    public String getEvent() {
        return timestamp + " " + message.toString();
    }

    public TypeOfEvents getTypeOfEvent() {
        return type;
    }

    public Object getMessage() {
        return message;
    }

    public String getTimestamp() {
        return timestamp;
    }


}
