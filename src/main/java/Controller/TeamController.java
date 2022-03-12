package Controller;

import Entity.Event;
import Entity.Worker;
import Entity.Team;
import Entity.Center;
import Observer.Observer;
import Observer.Subject;
import Repository.Repository;
import Enum.*;
import Entity.Entity;
import Request.TeamCreationRequest;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

// OGGETTO CHE SI OCCUPERà DI CONSERVARE/FORMARE I TEAM
public class TeamController extends Subject implements Observer {

    private static TeamController teamController;
    // LISTA DELLE ENTITà
    private List<Worker> policemen;
    private List<Worker> firemen;
    private List<Worker> doctors;
    private List<Worker> drivers;
    private List<Entity> entities;
    private int id;


    private TeamController() {
        entities = new ArrayList<Entity>();
        firemen = new ArrayList<Worker>();
        doctors = new ArrayList<Worker>();
        drivers = new ArrayList<Worker>();
        policemen = new ArrayList<Worker>();
        this.id = 0;
        setupEntities();
        setupWorkers();
        attach(Center.getInstance());
        System.out.println("TeamController download done!");
    }

    public static TeamController getInstance() {
        if(teamController == null) {
            teamController = new TeamController();
        }
        return teamController;
    }

    private void setupEntities() {
        try {
            ResultSet rs = Repository.getInstance().getStatementQuery().executeQuery("SELECT * FROM Entities");
            while(rs.next()) {
                Entity entity = new Entity(rs.getLong(1), rs.getString(2), rs.getString(3), rs.getString(4));
                entities.add(entity);
            }
        } catch (SQLException e) {
            System.err.println(e);
            return;
        }
    }

    private void setupWorkers() {
        for(Entity e: entities) {
            TypeOfJobs job = e.getJob();
            switch (job) {
                case POLICEMAN:
                    Worker policeman = new Worker(e);
                    policemen.add(policeman);
                    break;
                case FIREMAN:
                    Worker fireman = new Worker(e);
                    firemen.add(fireman);
                    break;
                case DRIVER:
                    Worker driver = new Worker(e);
                    drivers.add(driver);
                    break;
                case DOCTOR:
                    Worker doctor = new Worker(e);
                    doctors.add(doctor);
                    break;
                default:
                    break;
            }
        }
    }
    // NOTIFICA DI CREAZIONE
    @Override
    public void update(int id, Object obj, Event event) {
        if(event.getTypeOfEvent() != TypeOfEvents.REQUEST_TEAM) {
            return;
        }
        TypeOfJobs typeOfRequest = (TypeOfJobs) event.getMessage();
        switch ( typeOfRequest ) {
            case DOCTOR:
                System.out.println("Richiesta arrivata");
                createNewTeam(doctors, TypeOfJobs.DOCTOR);
                return;
            case FIREMAN:
                createNewTeam(firemen, TypeOfJobs.FIREMAN);
                return;
            case POLICEMAN:
                createNewTeam(policemen, TypeOfJobs.FIREMAN);
                return;
            default:
                break;
        }
    }

    @Override
    public void update(Object obj, Event event) {
        if(event.getTypeOfEvent() == TypeOfEvents.ATTACH) {
            System.out.println("Attach avvenuto con successo");
            attach((TeamCreationRequest)event.getMessage());
        }else if(event.getTypeOfEvent() == TypeOfEvents.DETTACH){
            System.out.println("Detach avvenuto con successo");
            detach((TeamCreationRequest)event.getMessage());
        }else if(event.getTypeOfEvent() == TypeOfEvents.MISSION_DONE) {
            Team team = (Team) event.getMessage();
            // LIBERIAMO LE ENTITA
            offDuty(team);
            System.out.println("Squadra liberata");
        }
        return;
    }

    private void offDuty(Team team) {
        switch (team.getTypeOfTeam()) {
            case DOCTOR:
                freeWorker(team.getEntity1());
                freeWorker(team.getEntity2());
                freeWorker(team.getDriver());
                break;
            case POLICEMAN:
                freeWorker(policemen, team.getEntity1());
                freeWorker(policemen, team.getEntity1());
                freeWorker(drivers, team.getDriver());
                break;
            case FIREMAN:
                freeWorker(firemen, team.getEntity1());
                freeWorker(firemen, team.getEntity1());
                freeWorker(drivers, team.getDriver());
                break;
        }
    }

    private void freeWorker(List<Worker> workers, Worker worker) {
        for(Worker e: workers) {
            if(worker == e) {
                System.out.println("Ciao ciao");
                e.setAvailability(true);
            }
        }
    }
    private void freeWorker(Worker worker) {
        worker.setAvailability(true);
    }

    private boolean checkTeamAvailability(List<Worker> list) {
        boolean check = false;
        for (Worker worker : list) {
            if( worker.getAvailability() == true ) {
                if(check == false) {
                    check = true;
                }else {
                    return true;
                }
            }
        }
        return false;
    }

    private void createNewTeam(List<Worker> list, TypeOfJobs typeOfTeam) {
        if( checkTeamAvailability(list) ) {
            if (checkDriverAvailability()) {
                System.out.println("Trovata disponibilità, creo e invio indietro alla centrale");
                notify( id, new Event(getTeam(list, typeOfTeam), TypeOfEvents.CREATION_DONE) );
                return;
            }
        }
        System.out.println("Disponibilità non trovata");
        notify( id, new Event(null, TypeOfEvents.CREATION_FAILED));
    }

    private boolean checkDriverAvailability() {
        for (Worker worker : drivers) {
            if( worker.getAvailability() == true ) {
                return true;
            }
        }
        return false;
    }

    /*
     * PRENDE IL PRIMO LAVORATORE LIBERO DELLA LISTA
     * */
    private Worker findFreeWoker(List<Worker> workers) {
        for(Worker e: workers) {
            if(e.getAvailability()==true) {
                e.setAvailability(false);
                return e;
            }
        }
        return null;
    }

    private Worker findFreeDriver() {
        for(Worker e: drivers) {
            if(e.getAvailability()==true) {
                e.setAvailability(false);
                return e;
            }
        }
        return null;
    }

    private Team getTeam(List<Worker> workers, TypeOfJobs typeOfTeam) {
        Team team = new Team(id++, findFreeWoker(drivers), findFreeWoker(workers), findFreeWoker(workers), typeOfTeam);
        return team;
    }

}
