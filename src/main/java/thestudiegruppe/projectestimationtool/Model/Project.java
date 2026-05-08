package thestudiegruppe.projectestimationtool.Model;

import java.util.List;

public class Project {

    private int id;
    private String name;
    private String email;
    private String password;
    private List<SubProject> subProjects;
    private User user;
    private Status status;

    public Project(int id, String name, String email, String password, User user, Status status) {

        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
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

    public String getEmail() {

        return email;
    }

    public void setEmail(String eemail) {

        this.email = email;
    }

    public String getPassword() {

        return password;
    }

    public void setPassword(String password) {

        this.password = password;
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
