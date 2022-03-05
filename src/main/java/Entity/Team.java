package Entity;

// RAPPRESENTA LA SQUADRA DA SOCCORSO, FORMATO DA 3 MEMBRI: pilota + 2 membri confonrmi all'emergenza creata

public class Team {

    private Long id;
    private Worker driver;
    private Worker entity1;
    private Worker entity2;

    public void sendEvents() throws InterruptedException {
        for(int i = 0; i < 4; i++) {
            driver.sendEvent();
            entity1.sendEvent();
            entity2.sendEvent();
            Thread.sleep(1000);
        }
    }
}
