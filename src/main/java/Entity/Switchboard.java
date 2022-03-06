package Entity;

import Observer.Observer;
import Observer.Subject;
import Enum.*;

// SI OCCUPERò DI RICEVERE L'EMERGENZA
public class Switchboard extends Subject implements Observer {

    private static Switchboard switchboard;

    private Switchboard() {

    }

    public Switchboard getInstance() {
        if(switchboard == null) {
            switchboard = new Switchboard();
        }
        return switchboard;
    }


    @Override
    public void update(Subject subject, Event event) {
        if(event.getTypeOfEvent() != TypeOfEvents.EMERGENCY ) {
            return;
        }

        switch (event.getMessage().toString()) {
            case "HOSPITAL":
                Event newEvent = new Event(Jobs.DOCTOR, TypeOfEvents.REQUEST_EMERGENCY);
                notify(newEvent);
                break;
            default:
                break;
        }
    }

}
