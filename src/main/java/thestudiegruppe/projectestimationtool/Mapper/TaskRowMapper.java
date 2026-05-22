package thestudiegruppe.projectestimationtool.Mapper;

import org.springframework.jdbc.core.RowMapper;
import thestudiegruppe.projectestimationtool.Model.Status;
import thestudiegruppe.projectestimationtool.Model.Task;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TaskRowMapper implements RowMapper<Task> {

    public Task mapRow(ResultSet rs, int rowNum) throws SQLException {

        return new Task(
                rs.getInt("task_id"),
                rs.getString("name"),
                rs.getString("description"),
                rs.getDouble("hourlyRate"),
                Status.valueOf(rs.getString("status")),
                rs.getInt("subProject_id")
        );
    }
}
