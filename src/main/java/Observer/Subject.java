package Observer;

import Entity.Event;

import java.util.ArrayList;
import java.util.List;

public class Subject {

    private List<Observer> subscribers = new ArrayList<>();

    public void notify(Event event) {
        subscribers.stream().forEach( x -> x.update(this, event));
    }
    public void notify(int id, Event event) {
        subscribers.stream().forEach( x -> {
            try {
                x.update( id, this, event);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    public void attach(Observer o) {
        this.subscribers.add(o);
    }

    public void detach(Observer o) {
        this.subscribers.remove(o);
    }

}
