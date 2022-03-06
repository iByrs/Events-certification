package Entity;

import Enum.*;
// DISPOSITIVO CHE SI OCCUPA DI INVIARE LE NOTIFICHE
public class Device {

    private Lines lines;
    private Entity entity;
    private String mission;

    public Device(Entity e, String mission, boolean role) {
        this.entity = e;
        this.mission = mission;
        this.lines = new Lines(entity, role);
    }

    public Event newNotify() {
        Event event = new Event("Mission: "+mission+" "+lines.getLine(), TypeOfEvents.MESSAGE);
        return event;
    }

}
