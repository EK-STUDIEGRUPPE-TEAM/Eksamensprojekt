package thestudiegruppe.projectestimationtool.Model;

import java.time.LocalDate;
import java.util.List;

public class Project {

    private Integer id;
    private String name;
    private String description;
    private LocalDate date;
    private List<SubProject> subProjects;
    private int userId;
    private Status status;

    public Project(Integer id, String name, String description, LocalDate date, int userId, Status status) {

        this.id = id;
        this.name = name;
        this.description = description;
        this.date = date;
        this.userId = userId;
        this.status = status;
    }

    public Project(String name, String description, int userId, Status status) {

        this.name = name;
        this.description = description;
        this.date = LocalDate.now();
        this.userId = userId;
        this.status = status;
    }

    public Project() {
    }

    public Integer getId() {

        return id;
    }

    public void setId(Integer id) {

        this.id = id;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {

        this.name = name;
    }

    public String getDescription() {

        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public int getUserId() {

        return userId;
    }

    public void setUserId(int userId) {

        this.userId = userId;
    }

    public Status getStatus() {

        return status;
    }

    public void setStatus(Status status) {

        this.status = status;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }


}
