package Observer;

import Entity.Event;

import java.util.*;

public class Subject {

    private Vector<Observer> subscribers = new Vector<>();

    public void notify(Event event) {
        subscribers.forEach( x -> x.update(event));
    }
    public void notify(int id, Event event) {
        subscribers.forEach(x -> {
            try {
                x.update(id, event);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    public void attach(Observer o) {
        subscribers.add(o);
    }

    public void detach(Observer o) {
        subscribers.remove(o);
    }

}
