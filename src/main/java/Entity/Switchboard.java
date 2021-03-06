package Entity;

import Observer.Observer;
import Observer.Subject;
import Enum.*;

// SI OCCUPERò DI RICEVERE L'EMERGENZA
public class Switchboard extends Subject implements Observer {

    private static Switchboard switchboard;

    private Switchboard() {
    }

    public static Switchboard getInstance() {
        if(switchboard == null) {
            switchboard = new Switchboard();
        }
        return switchboard;
    }


    @Override
    public void update(Event event) {
        if(event.getTypeOfEvent() != TypeOfEvents.EMERGENCY ) {
            return;
        }
        // SE SUPERA L'IF IL SUBJECT è UN EMERGENCY
        TypeOfEmergency emergency = (TypeOfEmergency) event.getMessage();
        switch (emergency) {
            case HOSPITAL:
                Event newEvent = new Event(TypeOfJobs.DOCTOR, TypeOfEvents.REQUEST_EMERGENCY);
                notify(newEvent);
                break;
            default:
                break;
        }
    }

    @Override
    public void update(int id, Event event) {
        //TODO
        return;
    }

}
