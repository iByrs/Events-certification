package Entity;

import Utility.TimestampEvent;

public class Event {
    private String message;
    private String timestamp;

    public Event(String message) {
        this.message = message;
        this.timestamp = TimestampEvent.getTime();
    }

    public String getEvent() {
        return message + " " + timestamp;
    }
}
