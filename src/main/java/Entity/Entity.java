package Entity;
import Enum.TypeOfJobs;

public class Entity {

    private Long id;
    private String name, surname;
    private TypeOfJobs job;

    public Entity() {

    }

    public Entity(Long id, String name, String surname, String job) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.job = TypeOfJobs.valueOf(job);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public TypeOfJobs getJob() {
        return job;
    }

    public void setJob(TypeOfJobs job) {
        this.job = job;
    }

    public String toString() {
        return "id:"+id+"_"+job+"_"+name+"_"+surname;
    }
}
