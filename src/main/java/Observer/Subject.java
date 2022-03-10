package Observer;

import Entity.Event;

import java.util.ArrayList;
import java.util.List;

public class Subject {

    private boolean changed = false;
    private List<Observer> subscribers = new ArrayList<>();

    public void notify(Event event) {
        if(!changed) {
            return;
        }
        subscribers.stream().forEach( x -> x.update(this, event));
        setChanged();
    }

    public void setChanged() {
        this.changed = true;
    }

    public void attach(Observer o) {
        this.subscribers.add(o);
    }

    public void detach(Observer o) {
        this.subscribers.remove(o);
    }

}
