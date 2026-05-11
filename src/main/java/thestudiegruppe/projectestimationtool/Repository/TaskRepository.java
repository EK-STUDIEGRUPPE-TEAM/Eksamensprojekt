package thestudiegruppe.projectestimationtool.Repository;

import org.apache.logging.log4j.util.Timer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import thestudiegruppe.projectestimationtool.Model.Status;
import thestudiegruppe.projectestimationtool.Model.Task;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class TaskRepository {

    private final JdbcTemplate jdbcTemplate;

    public TaskRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public static class TaskRowMapper implements RowMapper <Task> {

        @Override
        public Task mapRow(ResultSet rs, int rowNum) throws SQLException {
            Task task = new Task(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("description"),
                    rs.getDouble("hourlyRate"),
                    Status.valueOf(rs.getString("status")),
                    rs.getInt("subProject_id")
            );
            return task;
        }
    }

        public int addTask (Task task){
            String sql = "INSERT INTO Task (name, description, hourlyRate, status, subProject_id) VALUES (?, ?, ?, ?, ?)";
            return jdbcTemplate.update(sql, task.getName(), task.getDescription(), task.getHourlyRate(), task.getStatus(), task.getSubProjectId());
        }

        public List<Task> findAll() {
            String sql = "SELECT id, name, description, hourlyRate, status, subProject_id FROM Task";
            return jdbcTemplate.query(sql, new TaskRowMapper());
        }

        public List<Task> findTasksBySubProjectId(int subProjectId){
            String sql = "SELECT id, name, description, hourlyRate, status FROM Task WHERE subProject_id";
            return jdbcTemplate.query(sql, new TaskRowMapper(), subProjectId);
        }

        public void deleteTask (int id){
            String sql = "DELETE FROM Task WHERE id";
            jdbcTemplate.update(sql, id);
        }

        public int updateTask(Task task){
        String sql = "UPDATE Task SET name, description, hourlyRate, status WHERE id";
        return jdbcTemplate.update(sql, task.getName(), task.getDescription(), task.getHourlyRate(), task.getStatus());
        }

        public int deleteTaskBySubProjectId (int subProjectId){
        String sql = "DELETE Task WHERE subProject_id";
        return jdbcTemplate.update(sql, subProjectId);
        }

        public List<Task> findTaskPrice(double hourlyRate){
        String sql = "SELECT hourlyRate FROM Task";
        return jdbcTemplate.query(sql, new TaskRowMapper(), hourlyRate);
        }

}
