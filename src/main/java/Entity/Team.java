package Entity;

// RAPPRESENTA LA SQUADRA DA SOCCORSO, FORMATO DA 3 MEMBRI: pilota + 2 membri confonrmi all'emergenza creata

public class Team {

    private Long id;
    private Worker driver;
    private Worker entity1;
    private Worker entity2;

    /*
    * Il metodo deve permettere di far interaggire le entità tra loro e nel frattempo inviare i messaggi
    * */
    public Team(Long id, Worker driver, Worker entity1, Worker entity2) {
        this.id = id;
        this.driver = driver;
        this.entity1 = entity1;
        this.entity2 = entity2;
        entity1.setupForWork(true, id);
        entity2.setupForWork(false, id);
    }

    public void sendEvents() throws InterruptedException {
        for(int i = 0; i < 4; i++) {
            driver.sendEvent();
            entity1.sendEvent();
            entity2.sendEvent();
            Thread.sleep(1000);
        }
    }

    public String teamToString() {
        String msg = "Team "+id.toString()+" "+entity1.toString()+" "+entity2.toString()+" "+driver.toString();
        return msg;
    }
    
}
