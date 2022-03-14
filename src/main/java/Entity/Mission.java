package Entity;

import Observer.Subject;
import Enum.*;

public class Mission extends Subject implements Runnable {

    private Team team;
    private int id;

    public Mission(int id, Team team) {
        this.team = team;
        this.id = id;
    }

    private void startMission() throws InterruptedException {
        team.sendEvents();
    }
    private void endMission() {
        attach(Center.getInstance());
        Event event = new Event(team, TypeOfEvents.MISSION_DONE);
        notify(event);
    }

    @Override
    public void run() {
        try {
            startMission();
            endMission();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
