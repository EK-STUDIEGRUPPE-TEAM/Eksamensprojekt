package thestudiegruppe.projectestimationtool.Repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import thestudiegruppe.projectestimationtool.Mapper.TaskRowMapper;
import thestudiegruppe.projectestimationtool.Model.Task;

import java.util.List;

@Repository
public class TaskRepository {

    private final JdbcTemplate jdbcTemplate;

    public TaskRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

        public int addTask (Task task){
            String sql = "INSERT INTO Task (name, description, hourlyRate, status, subProject_id) VALUES (?, ?, ?, ?, ?)";
            return jdbcTemplate.update(sql, task.getName(), task.getDescription(), task.getHourlyRate(), task.getStatus().name(), task.getSubProjectId());
        }

        public List<Task> findAllTasks() {
            String sql = "SELECT id, name, description, hourlyRate, status, subProject_id FROM Task";
            return jdbcTemplate.query(sql, new TaskRowMapper());
        }

        public List<Task> findTasksBySubProjectId(int subProjectId){
            String sql = "SELECT id, name, description, hourlyRate, status FROM Task WHERE subProject_id = ?";
            return jdbcTemplate.query(sql, new TaskRowMapper(), subProjectId);
        }

        public void deleteTask (int id){
            String sql = "DELETE FROM Task WHERE id = ?";
            jdbcTemplate.update(sql, id);
        }

        public int updateTask(Task task){
        String sql = "UPDATE Task SET name = ?, description = ?, hourlyRate = ?, status = ? WHERE id = ?";
        return jdbcTemplate.update(sql, task.getName(), task.getDescription(), task.getHourlyRate(), task.getStatus().name(), task.getId());
        }

        public int deleteTaskBySubProjectId (int subProjectId){
        String sql = "DELETE FROM Task WHERE subProject_id = ?";
        return jdbcTemplate.update(sql, subProjectId);
        }
}
