package Entity;

import Observer.Subject;
import Enum.*;

import java.util.ArrayList;
import java.util.List;

public class Mission extends Subject implements Runnable {

    private int id;
    private Team team;
    private Emergency emergency;

    public Mission(int id, Team team) {
        this.team = team;
        this.emergency = team.getEmergency();
        this.id = id;
    }

    private void missionStarts() throws InterruptedException {
        team.sendEvents();
    }
    private void missionComplete() {
        attach(Center.getInstance());
        Event event = new Event(this, TypeOfEvents.MISSION_COMPLETE);
        notify(event);
    }

    @Override
    public void run() {
        try {
            missionStarts();
            missionComplete();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public int getMissionId() {
        return id;
    }

    public Team getTeam() {
        return team;
    }

    public Emergency getEmergency() {
        return emergency;
    }

    public List<String> getMissionEvents() {
        List<String> driverEvents = team.getDriver().getDevice().getStoredEvents();
        List<String> entity1Events = team.getEntity1().getDevice().getStoredEvents();
        List<String> entity2Events = team.getEntity2().getDevice().getStoredEvents();
        List<String> missionEvents = new ArrayList<>();
        for(int i = 0; i < 4; i++) {
            missionEvents.add(driverEvents.get(i));
            missionEvents.add(entity1Events.get(i));
            missionEvents.add(entity2Events.get(i));
        }
        return missionEvents;
    }
}
