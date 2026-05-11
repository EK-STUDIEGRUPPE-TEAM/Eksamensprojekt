package thestudiegruppe.projectestimationtool.Model;

public class SubProject {

    private int id;
    private String name;
    private String description;

    public SubProject() {
    }

    public SubProject(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;

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
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;

    }
}