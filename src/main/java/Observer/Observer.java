package Observer;

import Entity.Event;

public interface Observer {

    public void update(Object obj, Event event);
    public void update(int id, Object obj, Event event) throws InterruptedException;
}
