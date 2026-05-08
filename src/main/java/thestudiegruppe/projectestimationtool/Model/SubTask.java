package thestudiegruppe.projectestimationtool.Model;

public class SubTask {

    private int subTaskId;
    private String name;
    private String description;
    private int estimatedHours;
    private Status status;

    public SubTask() {
    }

    public SubTask(int subTaskId, String name, String description, int estimatedHours, Status status) {
        this.subTaskId = subTaskId;
        this.name = name;
        this.description = description;
        this.estimatedHours = estimatedHours;
        this.status = status;
    }

    public int getSubTaskId() {
        return subTaskId;
    }

    public void setSubTaskId(int subTaskId) {
        this.subTaskId = subTaskId;
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
}

