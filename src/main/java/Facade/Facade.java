package Facade;

import Blockchain.Blockchain;
import Controller.BlockController;
import Controller.TeamBuilder;
import Entity.Center;
import Entity.Emergency;
import Entity.Switchboard;
import Repository.Repository;
import Enum.TypeOfEmergency;

import java.util.Random;

public class Facade {

    private Repository repository;
    private Center center;
    private Switchboard switchboard;
    private TeamBuilder teamBuilder;
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
        teamBuilder = TeamBuilder.getInstance();
        blockController = BlockController.getInstance();
        blockchain = Blockchain.getInstance();
    }

    private void bindsEntities() {
        center.attach(teamBuilder);
        center.attach(blockController);
        switchboard.attach(center);
        blockController.attach(blockchain);
    }

    public void sendEvent() {
        int randomNumber = new Random().nextInt(100);
        switch (randomNumber % 3) {
            case 0:
                Emergency carCrash = new Emergency(0, TypeOfEmergency.CARCRASH);
                break;
            case 1:
                Emergency fire = new Emergency(1, TypeOfEmergency.FIRE);
                break;
            case 2:
                Emergency gunsFire = new Emergency(2, TypeOfEmergency.GUNSFIGHT);
                break;
        }

    }


}
