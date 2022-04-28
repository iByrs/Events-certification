package Entity;

import Enum.*;
import Observer.Subject;

import java.util.List;
import java.util.Random;

// DISPOSITIVO CHE SI OCCUPA DI INVIARE LE NOTIFICHE
public class Device extends Subject {

    private Lines lines;
    private Entity entity;
    private int id_team;
    private int field;

    public Device(Entity e, int id_team, boolean role) {
        this.entity = e;
        this.id_team = id_team;
        this.lines = new Lines(entity, role);
        attach(Center.getInstance());
        this.field = new Random().nextInt(50)+1;
    }

    // Prova ad inviare la notifica
    public String newNotify() {
        String line = lines.getLine();
        Event event = new Event("Mission " + id_team + ", " + line, TypeOfEvents.NOTIFICATION);
        notify(id_team, event);
        return line;
    }


    public List<String> getStoredEvents() {
        return lines.getLines();
    }
}
