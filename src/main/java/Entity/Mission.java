package Entity;

import Observer.Observer;
import Observer.Subject;
import Entity.*;
import Enum.*;

public class Mission extends Subject implements Runnable {

    private int id;
    private Team team;

    public Mission(Team team) {
        this.team = team;
        this.id = id;
    }

    private void end() {
        attach(Center.getInstance());
        Event event = new Event(team, TypeOfEvents.MISSION_DONE);
        notify(event);
    }

    @Override
    public void run() {
        System.out.println("Eseguito run");
        try {
            team.sendEvents();
            end();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
