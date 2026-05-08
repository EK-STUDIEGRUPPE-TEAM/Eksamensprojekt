package thestudiegruppe.projectestimationtool.Repository;

import org.springframework.jdbc.core.JdbcTemplate;
import thestudiegruppe.projectestimationtool.Model.SubTask;

import java.util.List;

public class SubTaskRepository {

    private final JdbcTemplate jdbcTemplate;

    public SubTaskRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void addSubTask(SubTask subTask){
    }

    public List<SubTask> findAllSubTask(){
        return null;
    }

    public void deleteSubTask(int id){
    }

    public void updateSubTask(SubTask subTask){
    }
}
