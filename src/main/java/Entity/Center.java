package Entity;

import Controller.TeamController;
import Observer.*;
import Enum.*;

import static Enum.TypeOfEvents.*;

// SI OCCUPA DI RICEVERE GLI EVENTI E SMISTARLI CORRETTAMENTE
public class Center extends Subject implements Observer{

    private static Center center;

    private Center() {
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
                System.out.println("Richiesta di soccorso arrivata alla centrale, contatto la centrale di "+ event.getMessage());
                Event e = new Event(event.getMessage(), REQUEST_TEAM);
                setChanged();
                notify(e);
                break;
            case CREATION_DONE:
                System.out.println("GRAZIE! Avvio la missione");
                return;
            case MISSION_DONE:
                return;
            case CREATION_FAILED:
                System.out.println("Peccato :(");
                return;
            default:
                return;
        }
    }

    @Override
    public void update(int id, Object obj, Event event) {
        return;
    }


}
