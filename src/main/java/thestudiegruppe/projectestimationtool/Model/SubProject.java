package thestudiegruppe.projectestimationtool.Model;

import java.util.List;

public class SubProject {

    private Integer id;
    private String name;
    private String description;
    private Project project;
    private List<Task> taskList;

    public SubProject() {
    }

    public SubProject(Integer id, String name, String description, Project project) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.project = project;
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
    public List<Task> getTaskList(){
        return taskList;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}