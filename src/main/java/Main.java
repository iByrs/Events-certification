import Controller.TeamController;
import Entity.Center;
import Entity.Emergency;
import Entity.Switchboard;
import Entity.Team;
import Enum.*;
import Repository.Repository;

public class Main {
    public static void main(String[] args) {
        Repository repository = Repository.getInstance();
        TeamController teamController = TeamController.getInstance();
    }
}
