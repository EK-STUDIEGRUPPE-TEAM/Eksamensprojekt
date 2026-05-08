package thestudiegruppe.projectestimationtool.Model;

import java.util.List;

public class Task {

    private int id;
    private String name;
    private String description;
    private double hourlyRate;
    private Status status;
    private List<SubTask> subTasks;
    private int subProjectId;

    public Task (int id, String name,String description, double hourlyRate, Status status, List<SubTask> subTasks, int subProjectId){
        this.id = id;
        this.name = name;
        this.description = description;
        this.hourlyRate = hourlyRate;
        this.status = status;
        this.subTasks = subTasks;
        this.subProjectId = subProjectId;
    }

    public Task(){

    }

    public int getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public String getDescription(){
        return description;
    }

    public double getHourlyRate(){
        return hourlyRate;
    }

    public Status getStatus(){
        return status;
    }

    public List<SubTask> getSubTasks(){
        return subTasks;
    }

    public int getSubProjectId(){
        return subProjectId;
    }
}
