package Request;

import Controller.TeamController;
import Entity.Event;
import Observer.Observer;
import Observer.Subject;
import Enum.*;
import Entity.*;
import Utility.Logger;

import java.lang.reflect.Type;

import static Enum.TypeOfEvents.*;

public class TeamCreationRequest extends Subject implements Runnable, Observer {

    private TypeOfJobs job;
    // L'ID è L'IDENTIFICATIVO DELLA RICHIESTA
    private int id;

    public TeamCreationRequest(int id, TypeOfJobs event) {
        attach(Center.getInstance());
        attach(TeamController.getInstance());
        this.job = event;
        this.id = id;
    }

    private void attachMe() {
        notify(new Event(this, ATTACH));
    }
    private void dettachMe() {
        notify(new Event(this, DETTACH));
    }

    public void request() {
        Event e = new Event(job , REQUEST_TEAM);
        notify(id, e);
    }

    @Override
    public void run() {
        Logger.out(true, "TeamThread "+id+" avviato.");
        attachMe();
        request();
    }

    @Override
    public void update(Object obj, Event event) {

    }

    @Override
    public void update(int id, Object obj, Event event) throws InterruptedException {
        if(this.id != id) {
            return;
        }
        switch (event.getTypeOfEvent()) {
            case CREATION_DONE:
                // CREAZIONE AVVENUTA CON SUCCESSO! NOTIFICHIAMO, E CI STACCHIAMO
                creationDone(event);
                return;
            case CREATION_FAILED:
                // CREAZIONE FALLITA, IL THREAD SI METTE IN ATTESA E RE INVIA LA RICHIESTA DI CREAZIONE
                Logger.out(true, "TeamThread " + id + ": TeamController doesn't create a new team. Wait.");
                Thread.sleep(2000);
                Event newRequest = new Event(job, REQUEST_TEAM);
                Logger.out(true, "TeamThread " + id + " " + newRequest.getMessage());
                notify(id, newRequest);
                return;
            default:
                break;
        }
    }

    private void creationDone(Event event) {
        Team team = (Team) event.getMessage();
        Event notifyCenter = new Event(team, TEAM_CREATION_DONE);
        notify(id, notifyCenter);
    }




}
