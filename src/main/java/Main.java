import Entity.Center;
import Entity.Emergency;
import Entity.Switchboard;
import Enum.*;
public class Main {
    public static void main(String[] args) {
        System.out.println("* TESTING");
        Center center = Center.getInstance();
        Switchboard switchboard = Switchboard.getInstance();
        switchboard.attach(center);
        Emergency emergency = new Emergency(TypeOfEmergency.HOSPITAL);

    }
}
