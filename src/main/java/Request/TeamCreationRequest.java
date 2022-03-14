package Request;

import Controller.TeamController;
import Entity.Event;
import Observer.Observer;
import Observer.Subject;
import Enum.*;
import Entity.*;
import Utility.Logger;

import static Enum.TypeOfEvents.*;

public class TeamCreationRequest extends Subject implements Runnable, Observer {

    private Event event;
    // L'ID è L'IDENTIFICATIVO DELLA RICHIESTA
    private int id;

    public TeamCreationRequest(int id, Event event) {
        attach(Center.getInstance());
        attach(TeamController.getInstance());
        this.event = event;
        this.id = id;
    }

    private void attachMe() {
        notify(new Event(this, ATTACH));
    }
    private void dettachMe() {
        notify(new Event(this, DETTACH));
    }

    public void request() {
        Event e = new Event(event.getMessage() , REQUEST_TEAM);
        notify(id, e);
    }

    @Override
    public void run() {
        attachMe();
        notify(id, event);
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
                Event newRequest = new Event(event.getMessage(), REQUEST_TEAM);
                Logger.out(true, newRequest.getMessage().toString());
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
