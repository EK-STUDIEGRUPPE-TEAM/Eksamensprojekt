package thestudiegruppe.projectestimationtool.Repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import thestudiegruppe.projectestimationtool.Mapper.ProjectRowMapper;
import thestudiegruppe.projectestimationtool.Mapper.SubProjectRowMapper;
import thestudiegruppe.projectestimationtool.Model.Project;
import thestudiegruppe.projectestimationtool.Model.SubProject;

import java.util.List;

@Repository
public class SubProjectRepository {

    private final JdbcTemplate jdbcTemplate;

    public SubProjectRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public void addSubProject(SubProject subProject) {

        String sql = "INSERT INTO SubProject(name, project_id) VALUES(?,?)";
        jdbcTemplate.update(
                sql,
                subProject.getName(),
                subProject.getProjectId()
        );
    }
    public List<SubProject> getSubProjectsByProjectId(int projectId) {

        String sql = "SELECT * FROM SubProject WHERE project_id = ?";
        return jdbcTemplate.query(sql, new SubProjectRowMapper(), projectId);
    }

    // kun admin og test
    public List<SubProject> getAllSubProjects() {

        String sql = "SELECT * FROM SubProject";
        return jdbcTemplate.query(sql, new SubProjectRowMapper());
    }



    public void updateSubProject(SubProject subProject) {

        String sql = "UPDATE SubProject SET name = ? WHERE subProject_id = ?";
        jdbcTemplate.update(
                sql,
                subProject.getName(),
                subProject.getId());
    }

    public SubProject findById(int id) {

        String sql = "SELECT * FROM SubProject WHERE subProject_id = ?";
        return jdbcTemplate.queryForObject(sql, new SubProjectRowMapper(), id);
    }

    public void deleteSubProject(int id) {

        String sql = "DELETE FROM SubProject WHERE subProject_id = ?";
        jdbcTemplate.update(sql, id);
    }
}

