package thestudiegruppe.projectestimationtool.Repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import thestudiegruppe.projectestimationtool.Mapper.SubProjectRowMapper;
import thestudiegruppe.projectestimationtool.Model.SubProject;

import java.util.List;

@Repository
public class SubProjectRepository {

    private final JdbcTemplate jdbcTemplate;

    public SubProjectRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

// Gemmer et nyt SubProject i databasen
    public void addSubProject(SubProject subProject) {
        String sql = "INSERT INTO SubProject(name, description, project_id) VALUES(?,?,?)";

        jdbcTemplate.update(sql, subProject.getName(), subProject.getDescription(), subProject.getProject().getId());
    }
// Henter all Subprjecets, som ghører til et bestemt Project
    public List<SubProject> getSubProjectsByProjectId(int projectId) {
        String sql = "SELECT * FROM SubProject WHERE project_id = ?";

        return jdbcTemplate.query(sql, new SubProjectRowMapper(), projectId);
    }
// Henter alle Subprojects fra databasen
    public List<SubProject> getAllSubProjects() {
        String sql = "SELECT * FROM SubProject";
        return jdbcTemplate.query(sql, new SubProjectRowMapper());
    }
// Opdaterer navn og beskrivelse på et eksisterende SubProject
    public void updateSubProject(SubProject subProject) {
        String sql = "UPDATE SubProject SET name = ?, description = ? WHERE subProject_id = ?";

        jdbcTemplate.update(sql, subProject.getName(), subProject.getDescription(), subProject.getId());
    }
// Sletter et SubProject ud fra dets id
    public void deleteSubProject(int id) {
        String sql = "DELETE FROM SubProject WHERE subProject_id = ?";

        jdbcTemplate.update(sql, id);
    }
}

