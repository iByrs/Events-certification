package Facade;

import Blockchain.Blockchain;
import Controller.BlockController;
import Controller.TeamController;
import Entity.Center;
import Entity.Emergency;
import Entity.Switchboard;
import Entity.Team;
import Repository.Repository;
import Enum.TypeOfEmergency;
import Utility.Logger;

public class Facade {

    private Repository repository;
    private Center center;
    private Switchboard switchboard;
    private TeamController teamController;
    private BlockController blockController;
    private Blockchain blockchain;
    public Facade () {
        initializeControllers();
        bindsEntities();
    }

    private void initializeControllers() {
        repository = Repository.getInstance();
        center = Center.getInstance();
        switchboard = Switchboard.getInstance();
        teamController = TeamController.getInstance();
        blockController = BlockController.getInstance();
        blockchain = Blockchain.getInstance();
    }

    private void bindsEntities() {
        center.attach(teamController);
        center.attach(blockController);
        switchboard.attach(center);
        //blockController.attach(blockchain);
    }

    public void sendEvent() {
        Emergency emergency = new Emergency(TypeOfEmergency.HOSPITAL);
    }


}
