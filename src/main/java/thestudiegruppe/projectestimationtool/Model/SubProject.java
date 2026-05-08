package thestudiegruppe.projectestimationtool.Model;

public class SubProject {

    private int subProjectId;
    private String name;
    private String description;

    public SubProject() {
    }

    public SubProject(int subProjectId, String name, String description) {
        this.subProjectId = subProjectId;
        this.name = name;
        this.description = description;

    }

    public int getSubProjectId() {
        return subProjectId;
    }

    public void setSubProjectId(int subProjectId) {
        this.subProjectId = subProjectId;
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