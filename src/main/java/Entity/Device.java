package Entity;

import Enum.*;
// DISPOSITIVO CHE SI OCCUPA DI INVIARE LE NOTIFICHE
public class Device {

    private Lines lines;
    private Entity entity;
    private long id_team;

    public Device(Entity e, long id_team, boolean role) {
        this.entity = e;
        this.id_team = id_team;
        this.lines = new Lines(entity, role);
    }

    public Event newNotify() {
        Event event = new Event("Mission: "+id_team+" "+lines.getLine(), TypeOfEvents.MESSAGE);
        return event;
    }

}
