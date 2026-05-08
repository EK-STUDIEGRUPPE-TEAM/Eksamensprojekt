package thestudiegruppe.projectestimationtool.Repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import thestudiegruppe.projectestimationtool.Model.Project;
import thestudiegruppe.projectestimationtool.Model.Task;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ProjectRepository {


    private final JdbcTemplate jdbcTemplate;


    public ProjectRepository(JdbcTemplate jdbcTemplate){

        this.jdbcTemplate = jdbcTemplate;
    }
    public static class projectRowMapper implements RowMapper <Project> {

        @Override
        public Project mapRow(ResultSet rs, int rowNum) throws SQLException {
            Project project = new Project(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getString("password"),
                    rs.getInt("user_id"),
                    rs.getStatus("status")
            );
            return project;
        }
    }


    public void add(Project project){

        String sql = "INSERT INTO Projects(name, email, password, user_id, status) VALUES(?,?,?,?,?)";

        jdbcTemplate.update(sql, project.getName());

    }

    public List<Project> findAll(){

        return null;
    }

    public void delete(int id){

        String sql = "DELETE from Projects where id(?)"

    }

    public int update(Project project){

        return null;
    }




}
