package thestudiegruppe.projectestimationtool.Mapper;

import org.springframework.jdbc.core.RowMapper;
import thestudiegruppe.projectestimationtool.Model.SubProject;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SubProjectRowMapper implements RowMapper<SubProject> {

    public SubProject mapRow(ResultSet rs, int rowNum) throws SQLException {

        return new SubProject(
                rs.getInt("subProject_id"),
                rs.getString("name"),
                rs.getInt("project_id")
        );
    }
}

