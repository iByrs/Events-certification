package Entity;

import Observer.*;
import Enum.*;
import Repository.Repository;
import Request.TeamBuildingRequest;
import Utility.Logger;
import Utility.TimestampEvent;

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
    public void update(Event event) {
        TypeOfEvents typeEvent = event.getTypeOfEvent();
        switch (typeEvent) {
            case MISSION_COMPLETE:
                missionComplete(event);
                break;
            case POLICE_DEPARTMENT:
                callTeamBuilder(event);
                break;
            case HOSPITAL:
                callTeamBuilder(event);
                break;
            case FIREMEN_DEPARTMENT:
                callTeamBuilder(event);
                break;
            default:
                break;
        }
    }

    @Override
    public void update(int id, Event event) {
        TypeOfEvents typeEvent = event.getTypeOfEvent();
        switch (typeEvent) {
            case MISSION_START:
                Thread t = new Thread(new Mission(id, (Team)event.getMessage()));
                t.start();
                return;
            case NOTIFICATION:
                Event message = new Event(event.getMessage(), CREATE_BLOCK);
                Logger.out(true, message.getMessage().toString());
                break;
            default:
                break;
        }
    }

    // L'EVENTO IN INGRESSO HA COME PAYLOAD UNA EMERGENZA
    private void callTeamBuilder(Event event) {
        Emergency emergency = (Emergency) event.getMessage();
        Logger.out(true,"CENTRALE: Arrivata una nuova notifica d'emergenza "+ emergency.getEmergency()  +" "+ emergency.getId());
        Repository.getInstance().insertNewEvent(event);
        TypeOfEmergency typeOfEmergency = emergency.getEmergency();
        switch (typeOfEmergency) {
            case CARCRASH:
                createTeamBuildingRequest(emergency, TypeOfJobs.DOCTOR);
                break;
            case FIRE:
                createTeamBuildingRequest(emergency, TypeOfJobs.FIREMAN);
                break;
            case GUNSFIGHT:
                createTeamBuildingRequest(emergency, TypeOfJobs.POLICEMAN);
                break;
            default:
                break;
        }
    }

    private void createTeamBuildingRequest(Emergency emergency, TypeOfJobs job) {
        Logger.out(true,"CENTRALE: Procedo con la creazione di una nuova squadra da soccorso, " + job);
        TeamBuildingRequest teamBuilderRequest = new TeamBuildingRequest(counter++, emergency,job);
        Thread t = new Thread(teamBuilderRequest);
        t.start();
    }


    // L'EVENTO IN INGRESSO E' UNA MISSIONE COMPLETATA
    private void missionComplete(Event event) {
        Mission mission = (Mission) event.getMessage();
        Repository.getInstance().insertNewEvent(event);
        Logger.out(true,"CENTRALE: Notifica di missione completata " + mission.getMissionId());
        // NOTIFICHIAMO IL CONTROLLER
        Event teamControllerNotify = new Event(mission.getTeam(), TEAM_JOB_COMPLETED);
        notify(teamControllerNotify);
        Repository.getInstance().insertNewEvent(teamControllerNotify);
        Logger.out(true,"CENTRALE: Avviata la procedura di recupero degli eventi della missione " + mission.getMissionId());
        String events = mission.getMissionEvents().toString();
        Logger.out(true,"CENTRALE: Notifiche recuperate \n" + events);
        Event writeOnBlockchain = new Event(events, CREATE_BLOCK);
        notify(writeOnBlockchain);
        Repository.getInstance().insertNewEvent(writeOnBlockchain);
    }
}
