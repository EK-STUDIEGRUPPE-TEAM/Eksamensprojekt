package thestudiegruppe.projectestimationtool.Repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import thestudiegruppe.projectestimationtool.Mapper.SubTaskRowMapper;
import thestudiegruppe.projectestimationtool.Model.SubTask;

import java.util.List;

@Repository
public class SubTaskRepository {

    private final JdbcTemplate jdbcTemplate;

    public SubTaskRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void addSubTask(SubTask subTask){
        String sql = "INSERT INTO SubTask (name, description, estimated_hours, status, task_id) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                subTask.getName(),
                subTask.getDescription(),
                subTask.getEstimatedHours(),
                subTask.getStatus().name(),
                subTask.getTaskId());
    }

    public List<SubTask> findAllSubTask(){
        String sql = "SELECT * FROM SubTask";
        return jdbcTemplate.query(sql, new SubTaskRowMapper());
    }

    public List<SubTask> findSubTaskByTaskId(int taskId){
        String sql = "SELECT subTask_id, name, description, estimated_hours, status, task_id FROM SubTask WHERE task_id = ?";
        return jdbcTemplate.query(sql, new SubTaskRowMapper(), taskId);
    }

    public void deleteSubTask(int id){
        String sql = "DELETE FROM SubTask WHERE subTask_id = ?";
        jdbcTemplate.update(sql, id);
    }

    public int updateSubTask(SubTask subTask){
        String sql = "UPDATE SubTask SET name = ?, description = ?, estimated_hours = ?, status = ?, task_id = ? WHERE subTask_id = ?";
        return jdbcTemplate.update(sql,
                subTask.getName(),
                subTask.getDescription(),
                subTask.getEstimatedHours(),
                subTask.getStatus().name(),
                subTask.getTaskId(),
                subTask.getId());
    }

    public int deleteSubTaskByTaskId(int taskId){
        String sql = "DELETE FROM SubTask WHERE task_id = ?";
        return jdbcTemplate.update(sql, taskId);
    }
}
