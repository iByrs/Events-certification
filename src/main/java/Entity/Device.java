package Entity;

import Enum.*;
import Observer.Subject;

// DISPOSITIVO CHE SI OCCUPA DI INVIARE LE NOTIFICHE
public class Device extends Subject {

    private Lines lines;
    private Entity entity;
    private int id_team;

    public Device(Entity e, int id_team, boolean role) {
        this.entity = e;
        this.id_team = id_team;
        this.lines = new Lines(entity, role);
        attach(Center.getInstance());
    }

    public Event newNotify() {
        Event event = new Event("Mission: "+id_team+" "+lines.getLine(), TypeOfEvents.MESSAGE);
        notify(id_team, event);
        return event;
    }

}
