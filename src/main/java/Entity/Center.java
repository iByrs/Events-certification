package Entity;

import Observer.*;
import Enum.*;

import static Enum.TypeOfEvents.REQUEST_EMERGENCY;

// SI OCCUPA DI RICEVERE GLI EVENTI E SMISTARLI CORRETTAMENTE
public class Center extends Subject implements Observer{

    private static Center center;

    public Center() {

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
                System.out.println("Messaggio arrivato");
                break;
            case CREATION_DONE:
                break;
            case MISSION_DONE:
                break;
            default:
                break;
        }
        return;
    }


}
