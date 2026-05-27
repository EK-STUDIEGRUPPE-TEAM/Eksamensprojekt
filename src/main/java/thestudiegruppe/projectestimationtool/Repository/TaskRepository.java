package thestudiegruppe.projectestimationtool.Repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import thestudiegruppe.projectestimationtool.Mapper.ProjectRowMapper;
import thestudiegruppe.projectestimationtool.Mapper.TaskRowMapper;
import thestudiegruppe.projectestimationtool.Model.Project;
import thestudiegruppe.projectestimationtool.Model.Task;

import java.util.List;

@Repository
public class TaskRepository {

    private final JdbcTemplate jdbcTemplate;

    public TaskRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void addTask(Task task) {

        String sql = "INSERT INTO Task (name, description, deadline, hourlyRate, status, subProject_id) VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(
                sql,
                task.getName(),
                task.getDescription(),
                task.getDeadline(),
                task.getHourlyRate(),
                task.getStatus().name(),
                task.getSubProjectId());
    }

    public List<Task> getAllTasks() {

        String sql = "SELECT * FROM Task";
        return jdbcTemplate.query(sql, new TaskRowMapper());
    }

    public List<Task> getTasksBySubProjectId(int subProjectId) {

        String sql = "SELECT * FROM Task WHERE subProject_id = ?";
        return jdbcTemplate.query(sql, new TaskRowMapper(), subProjectId);
    }

    public void deleteTask(int id) {

        String sql = "DELETE FROM Task WHERE task_id = ?";
        jdbcTemplate.update(sql, id);
    }

    public int updateTask(Task task) {

        String sql = "UPDATE Task SET name = ?, description = ?, deadline = ?, hourlyRate = ?, status = ? WHERE task_id = ?";
        return jdbcTemplate.update(
                sql,
                task.getName(),
                task.getDescription(),
                task.getDeadline(),
                task.getHourlyRate(),
                task.getStatus().name(),
                task.getId());
    }

    public int deleteTaskBySubProjectId(int subProjectId) {

        String sql = "DELETE FROM Task WHERE subProject_id = ?";
        return jdbcTemplate.update(sql, subProjectId);
    }

    public Task findById(int id) {

        String sql = "SELECT * FROM Task WHERE task_id = ?";
        return jdbcTemplate.queryForObject(sql, new TaskRowMapper(), id);
    }

}
