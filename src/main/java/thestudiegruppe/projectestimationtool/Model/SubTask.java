package thestudiegruppe.projectestimationtool.Model;

public class SubTask {

    private int id;
    private String name;
    private String description;
    private int estimatedHours;
    private Status status;
    private int taskId;

    public SubTask() {
    }

    public SubTask(int id, String name, String description, int estimatedHours, Status status) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.estimatedHours = estimatedHours;
        this.status = status;
        this.taskId = taskId;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getEstimatedHours() {
        return estimatedHours;
    }

    public void setEstimatedHours(int estimatedHours) {
        this.estimatedHours = estimatedHours;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }
}

