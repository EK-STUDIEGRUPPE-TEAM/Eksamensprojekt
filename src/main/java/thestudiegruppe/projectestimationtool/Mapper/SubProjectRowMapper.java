package thestudiegruppe.projectestimationtool.Mapper;

import org.springframework.jdbc.core.RowMapper;
import thestudiegruppe.projectestimationtool.Model.Project;
import thestudiegruppe.projectestimationtool.Model.SubProject;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SubProjectRowMapper implements RowMapper<SubProject> {

        public SubProject mapRow(ResultSet rs, int rowNum) throws SQLException {

            Project project = new Project();
            project.setId(rs.getInt("project_id"));

            SubProject subProject = new SubProject(
                    rs.getInt("subProject_id"),
                    rs.getString("name"),
                    rs.getString("description"),
                    project
            );
            return subProject;
        }
    }

