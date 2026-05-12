package thestudiegruppe.projectestimationtool.Model;

import java.util.List;

public class Task {

    private Integer id;
    private String name;
    private String description;
    private double hourlyRate;
    private Status status;
    private List<SubTask> subTasks;
    private int subProjectId;

    public Task(Integer id, String name, String description, double hourlyRate, Status status, int subProjectId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.hourlyRate = hourlyRate;
        this.status = status;
        this.subProjectId = subProjectId;
    }

    public Task() {
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

    public String getDescription() {
        return description;
    }

    public double getHourlyRate() {
        return hourlyRate;
    }

    public Status getStatus() {
        return status;
    }
    public List<SubTask> getSubTasks(){
        return subTasks;
    }

    public int getSubProjectId(){
        return subProjectId;
    }
}
