package Observer;

import Entity.Event;

public interface Observer {

    public void update(Subject subject, Event event);

}
