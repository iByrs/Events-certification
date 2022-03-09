package Entity;

import java.util.List;
import Enum.TypeOfJobs;
public class Lines {
    // QUESTA CLASSE SI OCCUPERà DI DEFINIRE LE CONVERSAZIONI
    // 4 FRASI CIASCUNA CLASSE
    private List<String> lines;
    private Entity entity;
    private boolean role;
    private int counter;

    public Lines(Entity e, boolean role) {
        this.entity = e;
        this.role = role;
        counter = 0;
        setupLines();
    }

    private void setupLines() {
        TypeOfJobs job = entity.getJob();
        switch( job ) {
            case DOCTOR:
                setupDoctorLines();
                break;
            case POLICEMAN:
                setupPolicemanLines();
                break;
            case FIREMAN:
                setupFiremanLines();
                break;
            case DRIVER:
                setupDriverLines();
                break;
            default:
                System.out.println("Lines: Not a valid input!");
                break;
        }
    }

    private void setupDoctorLines() {
        if(role == true) {
            lines.add("Visito l incidentato.");
            lines.add("L incidentato è svenuto, battito basso. ");
            lines.add("Procedo con l operazione ...");
            lines.add("L incidentato si è ripreso");
        }else {
            lines.add("Visito l incidentato.");
            lines.add("Spoglio l incidentato per svolgere l operazione di rianimazione.");
            lines.add("Procediamo con l operazione di soccorso.");
            lines.add("Carico l incidentato sul lettino. Carichiamo sull auto.");
        }
    }

    private void setupPolicemanLines() {
        if(role == true) {

        }else {

        }
    }

    private void setupFiremanLines() {
        if(role == true) {

        }else {

        }
    }

    private void setupDriverLines() {
        lines.add("Mi dirigo verso il luogo del misfatto.");
        lines.add("Arrivati nel luogo del misfatto. Spengo.");
        lines.add("Mi dirigo alla centrale, per depositare i messaggi.");
        lines.add("Arrivati alla centrale. Spengo i motori.");
    }

    public String getLine() {
        return entity.toString() + " " +lines.get(counter++);
    }

}
