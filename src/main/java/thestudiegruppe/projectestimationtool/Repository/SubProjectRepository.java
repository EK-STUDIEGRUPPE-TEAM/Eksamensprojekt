package thestudiegruppe.projectestimationtool.Repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import thestudiegruppe.projectestimationtool.Model.SubProject;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class SubProjectRepository {

    private JdbcTemplate jdbcTemplate;

    public SubProjectRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public static class SubProjectRowMapper implements RowMapper<SubProject>{

        @Override
        public SubProject mapRow(ResultSet rs, int rowNum) throws SQLException{

            SubProject subProject = new SubProject(
                    rs.getInt("subProject_id"),
                    rs.getString("name"),
                    rs.getString("description")
            );
            return subProject;
        }
    }

    public void addSubProject(SubProject subProject) {
        String sql = "INSERT INTO SubProject(name, description) VALUES(?,?)";

        jdbcTemplate.update(sql, subProject.getName(), subProject.getDescription());
    }

    public List<SubProject> getAllSubProjects() {
        String sql = "SELECT subProject_id, name, description, project_id FROM SubProject";

        return jdbcTemplate.query(sql, new SubProjectRowMapper());
    }

    public void updateSubProject(SubProject subProject) {
        String sql = "UPDATE SubProject SET name = ?, description = ? WHERE subProject_id";

        jdbcTemplate.update(sql, subProject.getName(), subProject.getDescription(), subProject.getId());
    }

    public void deleteSubProject(int id) {
        String sql = "DELETE FROM SubProject WHERE subProject_id = ?";

        jdbcTemplate.update(sql, id);
    }
}

