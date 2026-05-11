package thestudiegruppe.projectestimationtool.Repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import thestudiegruppe.projectestimationtool.Model.Project;
import thestudiegruppe.projectestimationtool.Model.Status;
import thestudiegruppe.projectestimationtool.Model.Task;
import thestudiegruppe.projectestimationtool.Model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.time.LocalDate;


public class ProjectRepository {


    private final JdbcTemplate jdbcTemplate;


    public ProjectRepository(JdbcTemplate jdbcTemplate) {

        this.jdbcTemplate = jdbcTemplate;
    }

    public static class projectRowMapper implements RowMapper<Project> {

        @Override
        public Project mapRow(ResultSet rs, int rowNum) throws SQLException {

            User user = new User();
            user.setId(rs.getInt("user_id"));

            Project project = new Project(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("description"),
                    rs.getDate("date").toLocalDate(),
                    user,
                    Status.valueOf((rs.getString("status")))
            );
            return project;
        }
    }


    public void add(Project project) {

        String sql = "INSERT INTO Projects(name, description, date, user_id, status ) VALUES(?,?,?,?,?)";

        jdbcTemplate.update(sql, project.getName(), project.getDescription(), project.getDate(), project.getUser().getId());

    }

    public List<Project> findAll() {

        String sql = "SELECT id, name, description, date, user_id, status from Projects";

        return jdbcTemplate.query(sql, new projectRowMapper());

    }

    public void delete(int id) {

        String sql = "DELETE from Projects where id(?)";

    }

    public int update(Project project) {

        return 0;
    }


}
