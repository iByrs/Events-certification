package Entity;
import Enum.*;

import java.lang.reflect.Type;
// RAPPRESENTA LA SQUADRA DA SOCCORSO, FORMATO DA 3 MEMBRI: pilota + 2 membri confonrmi all'emergenza creata

public class Team {

    private int id;
    private Worker driver;
    private Worker entity1;
    private Worker entity2;
    private TypeOfJobs typeOfTeam;
    /*
    * Il metodo deve permettere di far interaggire le entità tra loro e nel frattempo inviare i messaggi
    * */
    public Team(int id, Worker driver, Worker entity1, Worker entity2, TypeOfJobs typeOfTeam) {
        this.id = id;
        this.driver = driver;
        this.entity1 = entity1;
        this.entity2 = entity2;
        entity1.setupForWork(true, id);
        entity2.setupForWork(false, id);
        driver.setupForWork(true, id);
        this.typeOfTeam = typeOfTeam;
    }

    public void sendEvents() throws InterruptedException {
        for(int i = 0; i < 4; i++) {
            driver.sendEvent();
            entity1.sendEvent();
            entity2.sendEvent();
            Thread.sleep(500);
        }
    }

    public String teamToString() {
        String msg = "Team "+id+" "+entity1.toString()+" "+entity2.toString()+" "+driver.toString();
        return msg;
    }

    public Worker getDriver() {
        return driver;
    }
    public Worker getEntity1() {
        return entity1;
    }
    public Worker getEntity2() {
        return entity2;
    }
    public TypeOfJobs getTypeOfTeam() {
        return typeOfTeam;
    }
    public int getId() { return id; }
    
}
