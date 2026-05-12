package thestudiegruppe.projectestimationtool.Mapper;

import org.springframework.jdbc.core.RowMapper;
import thestudiegruppe.projectestimationtool.Model.Status;
import thestudiegruppe.projectestimationtool.Model.SubTask;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SubTaskRowMapper implements RowMapper<SubTask> {

    public SubTask mapRow (ResultSet rs, int rowNum) throws SQLException {
        SubTask subTask = new SubTask(
                rs.getInt("subTask_id"),
                rs.getString("name"),
                rs.getString("description"),
                rs.getInt("estimatedHours"),
                Status.valueOf(rs.getString("status")),
                rs.getInt("task_id")
        );
        return subTask;
    }

}
