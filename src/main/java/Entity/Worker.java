package Entity;


// CONTIENE TUTTE LE INFORMAZIONI DI UN ENTITà PER SVOLGERE IL SUO LAVORO E NOTIFICARE
public class Worker {

    private boolean availability;
    private Entity entity;
    private Device device;

    public Worker(Entity e) {
        this.entity = e;
        this.availability = true;
    }

    public void setupForWork(boolean role, String idMission) {
        this.device = new Device(entity, idMission, role);
        this.availability = false;
    }

    public boolean getAvailability() {
        return availability;
    }

    public void sendEvent() {
        device.newNotify();
    }


}
