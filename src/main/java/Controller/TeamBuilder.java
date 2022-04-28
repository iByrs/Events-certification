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
import Request.TeamBuildingRequest;
import Utility.Logger;
import Utility.TimestampEvent;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

// OGGETTO CHE SI OCCUPERà DI CONSERVARE/FORMARE I TEAM
public class TeamBuilder extends Subject implements Observer {

    private static TeamBuilder teamBuilder;
    // LISTA DELLE ENTITà
    private List<Worker> policemen;
    private List<Worker> firemen;
    private List<Worker> doctors;
    private List<Worker> drivers;
    private int id;


    private TeamBuilder() {
        firemen = new ArrayList<>();
        doctors = new ArrayList<>();
        drivers = new ArrayList<>();
        policemen = new ArrayList<>();
        this.id = 0;
        setupEntities();
        attach(Center.getInstance());
    }

    public static TeamBuilder getInstance() {
        if(teamBuilder == null) {
            teamBuilder = new TeamBuilder();
        }
        return teamBuilder;
    }

    private void setupEntities() {
        try {
            ResultSet rs = Repository.getInstance().getStatementQuery().executeQuery("SELECT * FROM Entities");
            while(rs.next()) {
                Entity entity = new Entity(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4));
                insertEntity(entity);
            }
        } catch (SQLException e) {
            System.err.println(e);
            return;
        }
    }

    private void insertEntity(Entity e) {
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
    // NOTIFICA DI CREAZIONE
    @Override
    public void update(int idRequest, Event event) {
        if(event.getTypeOfEvent() != TypeOfEvents.TEAM_BUILDING_REQUEST) {
            return;
        }
        TypeOfJobs typeOfRequest = (TypeOfJobs) event.getMessage();
        switch ( typeOfRequest ) {
            case DOCTOR:
                createNewTeam(doctors, idRequest, TypeOfJobs.DOCTOR);
                return;
            case FIREMAN:
                createNewTeam(firemen, idRequest, TypeOfJobs.FIREMAN);
                return;
            case POLICEMAN:
                createNewTeam(policemen, idRequest, TypeOfJobs.FIREMAN);
                return;
            default:
                break;
        }
    }

    @Override
    public void update(Event event) {
        if(event.getTypeOfEvent() == TypeOfEvents.ATTACH) {
            Logger.out(true,"TEAMBUILDER: Richiesta "+((TeamBuildingRequest)event.getMessage()).getId()+" ricevuta");
            attach((TeamBuildingRequest)event.getMessage());
        }else if(event.getTypeOfEvent() == TypeOfEvents.DETACH){
            detach((TeamBuildingRequest)event.getMessage());
        }else if(event.getTypeOfEvent() == TypeOfEvents.TEAM_JOB_COMPLETED) {
            Team team = (Team) event.getMessage();
            Logger.out(true,"TEAMBUILDER: Richiesta di liberare i membri del team " + team.getId()+ " associata alla missione "+ team.getEmergency().getEmergency());
            offDuty(team);
            Logger.out(true,"TEAMBUILDER: I membri della squadra sono stati liberati. Squadra " + team.getId() + " " + team.getTypeOfTeam() + " sulla missione " + team.getEmergency().getId());
        }
        return;
    }

    private void offDuty(Team team) {
        team.getEntity1().setAvailability(true);
        team.getEntity2().setAvailability(true);
        team.getDriver().setAvailability(true);
    }

    private boolean checkTeamAvailability(List<Worker> list) {
        boolean check = false;
        for (Worker worker : list) {
            if( worker.getAvailability() == true ) {
                if(!check) {
                    check = true;
                }else {
                    return true;
                }
            }
        }
        return false;
    }

    private void createNewTeam(List<Worker> list, int teamId, TypeOfJobs typeOfTeam) {
        Logger.out(true,"TEAMBUILDER: verifica di disponibilità per la creazione di un team da soccorso formato da " + typeOfTeam+".");
        if( checkTeamAvailability(list) ) {
            if (checkDriverAvailability()) {
                Logger.out(true,"TEAMBUILDER: Controllo avvenuto, squadra creata con successo.");
                Team team = getTeam(list, typeOfTeam);
                Logger.out(true,"TEAMBUILDER: Squadra creata e formata dai membri: \n"+team.getDriver().toString()+"\n"+team.getEntity1().toString()+"\n"+team.getEntity2());
                Logger.out(true, "TEAMBUILDER: Procedo con l'invio della squadra alla richiesta numerata con " + teamId);
                notify( teamId, new Event(team, TypeOfEvents.TEAM_BUILDING_SUCCEEDED) );
                return;
            }
        }
        Logger.out(true,"TEAMBUILDER: Controllo completato. Squadra "+ typeOfTeam + " non creata, attendere.");
        notify( teamId, new Event( typeOfTeam, TypeOfEvents.TEAM_BUILDING_FAILED));
    }

    private boolean checkDriverAvailability() {
        for (Worker worker : drivers) {
            if(worker.getAvailability()) {
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
            if(e.getAvailability()) {
                e.setAvailability(false);
                return e;
            }
        }
        return null;
    }

    private Team getTeam(List<Worker> workers, TypeOfJobs typeOfTeam) {
        Team team = new Team(id++, Objects.requireNonNull(findFreeWoker(drivers)), Objects.requireNonNull(findFreeWoker(workers)), Objects.requireNonNull(findFreeWoker(workers)), typeOfTeam);
        return team;
    }

}
