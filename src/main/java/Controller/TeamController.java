package Controller;

import Entity.Event;
import Entity.Worker;
import Entity.Team;
import Observer.Observer;
import Observer.Subject;
import Repository.Repository;
import Enum.*;
import Entity.Entity;

import java.nio.channels.WritePendingException;
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
    private long id;


    private TeamController() {
        entities = new ArrayList<Entity>();
        firemen = new ArrayList<Worker>();
        doctors = new ArrayList<Worker>();
        drivers = new ArrayList<Worker>();
        policemen = new ArrayList<Worker>();
        this.id = 0L;
        setupEntities();
        setupWorkers();
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
    public void update(Object obj, Event event) {

        TypeOfEvents typeOfEvent = event.getTypeOfEvent();
        switch ( typeOfEvent ) {
            case TEAM_DOCTOR:
                if( checkTeamAvailability(doctors) ) {
                    if (checkDriverAvailability()) {

                    }
                }
                break;
            case TEAM_FIREMAN:

                break;
            case TEAM_POLICEMAN:

                break;
            default:
                return;
        }

    }

    public boolean checkTeamAvailability(List<Worker> list) {
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

    public boolean checkDriverAvailability() {
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

    private Team getTeam(List<Worker> workers)  {
        Team team = new Team(id++, findFreeDriver(), findFreeWoker(workers), findFreeWoker(workers));
        return team;
    }



}
