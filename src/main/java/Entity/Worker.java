package Entity;


// CONTIENE TUTTE LE INFORMAZIONI DI UN ENTITà PER SVOLGERE IL SUO LAVORO E NOTIFICARE
public class Worker {

    private boolean availability;
    private Entity entity;
    private Device device;

    public Worker(Entity e) {
        this.entity = e;
        this.availability = true;
        this.device = null;
    }

    public void setupForWork(boolean role, int idTeam) {
        this.device = new Device(entity, idTeam, role);
        this.availability = false;
    }

    public boolean getAvailability() {
        return availability;
    }

    public void setAvailability(boolean state) {
        this.availability = state;
    }

    public void sendEvent() {
        device.newNotify();
    }
    public String toString() {
        return entity.toString();
    }

}
