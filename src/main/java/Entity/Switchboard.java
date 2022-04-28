package Entity;

import Observer.Observer;
import Observer.Subject;
import Enum.*;
import Utility.Logger;
import Utility.TimestampEvent;

import java.lang.reflect.Type;

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
        TypeOfEmergency emergency = ((Emergency) event.getMessage()).getEmergency();
        Logger.out(true, "CENTRALINO: nuovo evento di pericolo ricevuto " + emergency);
        switch (emergency) {
            case CARCRASH:
                Logger.out(true, "CENTRALINO: contatto la centrale " + TypeOfEvents.HOSPITAL);
                Event contactHospital = new Event(event.getMessage(), TypeOfEvents.HOSPITAL);
                notify(contactHospital);
                break;
            case GUNSFIGHT:
                Logger.out(true, "CENTRALINO: contatto la centrale " + TypeOfEvents.POLICE_DEPARTMENT);
                Event contactPoliceDepartment = new Event(event.getMessage(), TypeOfEvents.POLICE_DEPARTMENT);
                notify(contactPoliceDepartment);
                break;
            case FIRE:
                Logger.out(true, "CENTRALINO: contatto la centrale " + TypeOfEvents.FIREMEN_DEPARTMENT);
                Event contactFiremenDepartment = new Event(event.getMessage(), TypeOfEvents.FIREMEN_DEPARTMENT);
                notify(contactFiremenDepartment);
                break;
            default:
                break;
        }
    }

    @Override
    public void update(int id, Event event) {
    }

}
