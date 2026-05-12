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


    public void add(Project project) {

        String sql = "INSERT INTO Projects(name, description, date, user_id, status) VALUES(?,?,?,?,?)";

        jdbcTemplate.update(sql, project.getName(), project.getDescription(), project.getDate(), project.getUser().getId(), project.getStatus().name());

    }

    public List<Project> findAll() {

        String sql = "SELECT id, name, description, date, user_id, status from Projects";

        return jdbcTemplate.query(sql, new ProjectRowMapper());

    }

    public void delete(int id) {

        String sql = "DELETE FROM Projects WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    public int update(Project project) {
        String sql = "UPDATE Projects SET name = ?, description = ?, date = ?, user_id = ?, status = ? WHERE id = ?";
        return jdbcTemplate.update(sql, project.getName(), project.getDescription(), project.getDate(), project.getUser().getId(), project.getStatus().name(), project.getId());
    }


    public Project findById(int id) {
        String sql = "SELECT * FROM Projects WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, new ProjectRowMapper());
    }


    public List<Project> findByUser(User user) {
        String sql = "SELECT * FROM Projects WHERE user_id = ?";

        return jdbcTemplate.query(sql, new Object[]{user.getId()}, new ProjectRowMapper());
    }
}
