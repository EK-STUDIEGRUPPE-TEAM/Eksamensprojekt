package thestudiegruppe.projectestimationtool.Model;

import java.time.LocalDate;
import java.util.List;

public class Project {

    private int id;
    private String name;
    private String description;
    private LocalDate date;
    private List<SubProject> subProjects;
    private User user;
    private Status status;

    public Project(int id, String name, String description, LocalDate date, User user, Status status) {

        this.id = id;
        this.name = name;
        this.description = description;
        this.date = date;
        this.user = user;
        this.status = status;
    }

    public Project(String name, String description, User user, Status status){

        this.name = name;
        this.description = description;
        this.date = LocalDate.now();
        this.user = user;
        this.status = status;
    }

    public Project() {
    }

    public int getId() {

        return id;
    }

    public void setId(int id) {

        this.id = id;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {

        this.name = name;
    }

    public String getDescription(){

        return description;
    }

    public LocalDate getDate() {
        return date;
    }



    public List<SubProject> getSubProjects() {

        return subProjects;
    }

    public void setSubProjects(List<SubProject> subProjects) {

        this.subProjects = subProjects;
    }

    public User getUser() {

        return user;
    }

    public void setUser(User user) {

        this.user = user;
    }

    public Status getStatus() {

        return status;
    }

    public void setStatus(Status status) {

        this.status = status;
    }



}
