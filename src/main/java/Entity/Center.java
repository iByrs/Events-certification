package Entity;

import Observer.*;
import Enum.*;
import Request.TeamCreationRequest;
import Utility.Logger;

import static Enum.TypeOfEvents.*;

// SI OCCUPA DI RICEVERE GLI EVENTI E SMISTARLI CORRETTAMENTE
public class Center extends Subject implements Observer{

    private static Center center;
    private int counter;

    private Center() {
        counter = 0;
    }

    public static Center getInstance() {
        if(center == null) {
            center = new Center();
        }
        return center;
    }

    @Override
    public void update(Object subject, Event event) {
        TypeOfEvents typeEvent = event.getTypeOfEvent();
        switch (typeEvent) {
            case REQUEST_EMERGENCY:
                sendRequestNewTeam(event);
                break;
            case MISSION_DONE:
                missionDone(event);
                return;
            default:
                return;
        }
    }

    @Override
    public void update(int id, Object obj, Event event) {
        TypeOfEvents typeEvent = event.getTypeOfEvent();
        switch (typeEvent) {
            case TEAM_CREATION_DONE:
                Thread t = new Thread(new Mission(id, (Team)event.getMessage()));
                t.start();
                return;
            case MESSAGE:
                Logger.out(true, (String) event.getMessage());
                break;
            default:
                return;
        }
    }

    private void sendRequestNewTeam(Event event) {
        Logger.out(true,"Center: Distress call received, contact the team base "+ event.getMessage());
        Event teamRequest = new Event(event.getMessage(), REQUEST_TEAM);
        TeamCreationRequest teamCreationRequest = new TeamCreationRequest(counter++, teamRequest);
        Thread t = new Thread(teamCreationRequest);
        t.start();
    }

    private void missionDone(Event event) {
        // MISSIONE COMPLETATA CON SUCCESSO. NOTIFICARE IL TEAM CONTROLLER - SCRITTURA SU BLOCKCHAIN
        Logger.out(true, "Center: Re-entry permit confirmed for the team " + ((Team)event.getMessage()).teamToString());
        // NOTIFICHIAMO IL CONTROLLER
        Event teamControllerNotify = new Event(event.getMessage(), MISSION_DONE);
        notify(teamControllerNotify);
        Event writeOnBlockchain = new Event((Team)event.getMessage(), CREATE_BLOCK);
        notify(writeOnBlockchain);
    }
}
