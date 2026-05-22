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


    public int add(Project project, int userId) {

        /* Vi indsætter et nyt projekt i databasen.
           userId kommer fra sessionen og gemmes i kolonnen user_id */
        String sql = "INSERT INTO Project(name, description, date, budget, status, user_id) VALUES(?,?,?,?,?,?)";
        return jdbcTemplate.update(
                sql,
                project.getName(),
                project.getDescription(),
                project.getDate(),
                project.getBudget(),
                project.getStatus().name(),
                userId
        );
    }

    public List<Project> findAll() {

        /* Henter alle projekter.
           Denne metode bruges måske kun til admin/test,
           fordi normale brugere kun skal se deres egne projekter */
        String sql = "SELECT * FROM Project";

        return jdbcTemplate.query(sql, new ProjectRowMapper());
    }

    public void delete(int id) {

        /* Sletter projektet ud fra project_id */
        String sql = "DELETE FROM Project WHERE project_id = ?";

        jdbcTemplate.update(sql, id);
    }

    public int update(Project project) {

        /* Opdaterer projektet ud fra project_id.
           user_id gemmes også, så projektet stadig tilhører samme bruger */
        String sql = "UPDATE Project SET name = ?, description = ?, date = ?, budget = ?, status = ?, user_id = ? WHERE project_id = ?";
        return jdbcTemplate.update(sql,
                project.getName(),
                project.getDescription(),
                project.getDate(),
                project.getBudget(),
                project.getStatus().name(),
                project.getUserId(),
                project.getId()
        );
    }


    public Project findById(int id) {
        String sql = "SELECT * FROM Project WHERE project_id = ?";
        return jdbcTemplate.queryForObject(sql, new ProjectRowMapper(), id);
    }


    public List<Project> findByUserId(int userId) {
        String sql = "SELECT * FROM Project WHERE user_id = ?";

        return jdbcTemplate.query(sql, new ProjectRowMapper(), userId);
    }

}
