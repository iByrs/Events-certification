package Entity;

import Controller.TeamController;
import Observer.*;
import Enum.*;
import Request.TeamCreationRequest;

import static Enum.TypeOfEvents.*;

// SI OCCUPA DI RICEVERE GLI EVENTI E SMISTARLI CORRETTAMENTE
public class Center extends Subject implements Observer{

    private static Center center;
    private int team_counter;
    private int mission_counter;

    private Center() {
        team_counter = 0;
        mission_counter = 0;
    }

    public static Center getInstance() {
        if(center == null) {
            center = new Center();
        }
        return center;
    }


    @Override
    public void update(Object subject, Event event) {
        TypeOfEvents type = event.getTypeOfEvent();
        switch (type) {
            case REQUEST_EMERGENCY:
                sendRequestNewTeam(event);
                break;
            case TEAM_CREATION_DONE:
                // CREIAMO IL THREAD MISSIONE e AVVIAMO
                Thread t = new Thread(new Mission((Team)event.getMessage()));
                t.start();
                return;
            case MISSION_DONE:
                // MISSIONE COMPLETATA CON SUCCESSO. NOTIFICARE IL TEAM CONTROLLER - SCRITTURA SU BLOCKCHAIN
                System.out.println("Squadra rientrata " + ((Team)event.getMessage()).teamToString() );
                // NOTIFICHIAMO IL CONTROLLER
                notify(event);
                return;
            default:
                return;
        }
    }

    private void sendRequestNewTeam(Event event) {
        System.out.println("Richiesta di soccorso arrivata alla centrale, contatto la centrale di "+ event.getMessage());
        Event e = new Event(event.getMessage(), REQUEST_TEAM);
        TeamCreationRequest teamCreationRequest = new TeamCreationRequest(team_counter++, e);
        Thread t = new Thread(teamCreationRequest);
        t.start();
    }

    @Override
    public void update(int id, Object obj, Event event) {
        TypeOfEvents type = event.getTypeOfEvent();
        switch (type) {
            case TEAM_CREATION_DONE:
                // CREIAMO IL THREAD MISSIONE e AVVIAMO
                Thread t = new Thread(new Mission((Team)event.getMessage()));
                t.start();
                return;
            case MESSAGE:
                System.out.println("Messaggio arrivato: "+event.getMessage());
                break;
            default:
                return;
        }
    }


}
