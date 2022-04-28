package Entity;

// CONTIENE TUTTE LE INFORMAZIONI DI UN ENTITà PER SVOLGERE IL SUO LAVORO E NOTIFICARE
public class Worker extends Entity{

    private boolean availability;
    private Device device;

    public Worker(String id, String name, String surname, String job) {
        super(id, name, surname, job);
        this.availability = true;
    }
    public Worker(Entity e) {
        super(e.getId(), e.getName(), e.getSurname(), e.getJob().toString());
        this.availability = true;
    }
    public void setupForWork(boolean role, int idTeam) {
        this.device = new Device(this, idTeam, role);
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

    public Device getDevice() {
        return device;
    }
}
