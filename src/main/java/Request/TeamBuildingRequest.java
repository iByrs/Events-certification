package Request;

import Controller.TeamBuilder;
import Entity.Event;
import Observer.Observer;
import Observer.Subject;
import Enum.*;
import Entity.*;
import Repository.Repository;
import Utility.Logger;
import Utility.TimestampEvent;

import static Enum.TypeOfEvents.*;

public class TeamBuildingRequest extends Subject implements Runnable, Observer {

    private TypeOfJobs job;
    private int id;
    private Emergency emergency;

    public TeamBuildingRequest(int id, Emergency emergency, TypeOfJobs job) {
        this.job = job;
        this.id = id;
        this.emergency = emergency;
        attach(Center.getInstance());
        attach(TeamBuilder.getInstance());
    }

    private void attachMe() {
        notify(new Event(this, ATTACH));
    }

    private void detachMe() {
        notify(new Event(this, DETACH));
    }

    @Override
    public void run() {
        attachMe();
        sendRequest();
    }

    @Override
    public void update(Event event) {
        return;
    }

    @Override
    public void update(int id, Event event) throws InterruptedException {
        if(this.id != id) {
            return;
        }
        switch (event.getTypeOfEvent()) {
            case TEAM_BUILDING_SUCCEEDED:
                // CREAZIONE AVVENUTA CON SUCCESSO! NOTIFICHIAMO, E CI STACCHIAMO
                Logger.out(true, "Richiesta " + id + ": Richiesta soddisfatta, inoltro alla centrale il team");
                creationDone(event);
                return;
            case TEAM_BUILDING_FAILED:
                // CREAZIONE FALLITA, IL THREAD SI METTE IN ATTESA E RE INVIA LA RICHIESTA DI CREAZIONE
                Logger.out(true, "Richiesta " + id + ": Ricevuto la notifica del fallimento della creazione del team");
                Thread.sleep(1000);
                sendRequest();
                return;
            default:
                break;
        }
    }

    public void sendRequest() {
        Logger.out(true,"Richiesta "+id+": richiesta aperta con successo, inoltro al TeamBuilder una richiesta di creazione di una squadra di " + job);
        Event e = new Event(job , TEAM_BUILDING_REQUEST);
        notify(id, e);
        Repository.getInstance().insertNewEvent(e);
    }


    private void creationDone(Event event) {
        Team team = (Team) event.getMessage();
        team.setEmergency(emergency);
        Event notifyCenter = new Event(team, MISSION_START);
        notify(id, notifyCenter);
    }

    public int getId() {
        return id;
    }


}
