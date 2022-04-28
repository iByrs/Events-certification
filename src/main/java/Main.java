import Entity.Emergency;
import Facade.Facade;
import Utility.Logger;
import Enum.*;
import java.util.concurrent.ExecutionException;

public class Main {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        Logger.clean();
        Facade facade = new Facade();
        Emergency emergency = new Emergency(0, TypeOfEmergency.CARCRASH);
    }
}
