package Request;

import Controller.TeamController;
import Entity.Event;
import Observer.Observer;
import Observer.Subject;
import Enum.*;
import Entity.*;

import static Enum.TypeOfEvents.*;

public class TeamCreationRequest extends Subject implements Runnable, Observer {

    private Event event;
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
        Event e = new Event( event.getMessage() , REQUEST_TEAM);
        setChanged();
        notify(e);
    }

    @Override
    public void run() {

    }

    @Override
    public void update(Object obj, Event event) {

    }

    @Override
    public void update(int id, Object obj, Event event) {

    }
}
