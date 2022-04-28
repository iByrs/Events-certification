package Observer;

import Entity.Event;

public interface Observer {
    public void update(Event event);
    public void update(int id, Event event) throws InterruptedException;
}
