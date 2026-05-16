package thestudiegruppe.projectestimationtool.Repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import thestudiegruppe.projectestimationtool.Mapper.ProjectRowMapper;
import thestudiegruppe.projectestimationtool.Model.Project;
import thestudiegruppe.projectestimationtool.Model.User;

import java.util.List;

@Repository
public class ProjectRepository {


    private final JdbcTemplate jdbcTemplate;


    public ProjectRepository(JdbcTemplate jdbcTemplate) {

        this.jdbcTemplate = jdbcTemplate;
    }


    public int add(Project project) {

        String sql = "INSERT INTO Project(name, description, date, user_id, status) VALUES(?,?,?,?,?)";
        return jdbcTemplate.update(sql, project.getName(), project.getDescription(), project.getDate(), project.getUserId(), project.getStatus().name());
    }

    public List<Project> findAll() {

        String sql = "SELECT * FROM Project";

        return jdbcTemplate.query(sql, new ProjectRowMapper());
    }

    public void delete(int id) {
        String sql = "DELETE FROM Project WHERE project_id = ?";
        jdbcTemplate.update(sql, id);
    }

    public int update(Project project) {
        String sql = "UPDATE Project SET name = ?, description = ?, date = ?, user_id = ?, status = ? WHERE project_id = ?";
        return jdbcTemplate.update(sql,
                project.getName(),
                project.getDescription(),
                project.getDate(),
                project.getUserId(),
                project.getStatus().name(),
                project.getId());
    }


    public Project findById(int id) {
        String sql = "SELECT * FROM Project WHERE user_id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, new ProjectRowMapper());
    }


    public List<Project> findByUserId(int userId) {
        String sql = "SELECT * FROM Project WHERE user_id = ?";

        return jdbcTemplate.query(sql, new ProjectRowMapper(), userId);
    }
}
