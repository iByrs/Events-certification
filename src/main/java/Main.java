import Controller.TeamController;

import Facade.Facade;
import Repository.Repository;

public class Main {
    public static void main(String[] args) {
        Facade facade = new Facade();
        facade.sendEvent();
    }
}
