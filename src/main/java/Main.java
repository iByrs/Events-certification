import Facade.Facade;
import Utility.Logger;

import java.util.concurrent.ExecutionException;

public class Main {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        Logger.clean();
        Facade facade = new Facade();
        for(int i = 0; i < 6; i++) {
            facade.sendEvent();
            Thread.sleep(1000);
        }
    }
}
