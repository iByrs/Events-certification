package Facade;

import Controller.TeamController;
import Entity.Center;
import Entity.Emergency;
import Entity.Switchboard;
import Entity.Team;
import Repository.Repository;
import Enum.TypeOfEmergency;
public class Facade {

    private Repository repository;
    private Center center;
    private Switchboard switchboard;
    private TeamController teamController;

    public Facade () {
        initializeControllers();
        bindsEntities();
    }

    private void initializeControllers() {
        repository = Repository.getInstance();
        center = Center.getInstance();
        switchboard = Switchboard.getInstance();
        teamController = TeamController.getInstance();
    }

    private void bindsEntities() {
        center.attach(teamController);
        switchboard.attach(center);

    }

    public void sendEvent() {
        Emergency emergency = new Emergency(TypeOfEmergency.HOSPITAL);
    }


}
