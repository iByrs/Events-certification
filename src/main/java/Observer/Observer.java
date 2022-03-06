package Observer;

import Entity.Event;

public interface Observer {

    public void update(Object obj, Event event);

}
