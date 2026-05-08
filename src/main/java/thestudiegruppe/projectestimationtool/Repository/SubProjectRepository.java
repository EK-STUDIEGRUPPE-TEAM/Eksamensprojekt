package thestudiegruppe.projectestimationtool.Repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import thestudiegruppe.projectestimationtool.Model.SubProject;

import java.util.List;

@Repository
public class SubProjectRepository {

    private JdbcTemplate jdbcTemplate;

    public SubProjectRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void addSubProject(SubProject subProject) {
    }

    public List<SubProject> getAllSubProjects() {
        return null;
    }

    public void updateSubProject(SubProject subProject) {

    }

    public void deleteSubProject(int id) {
    }
}

