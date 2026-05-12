package thestudiegruppe.projectestimationtool.Mapper;

import org.springframework.jdbc.core.RowMapper;
import thestudiegruppe.projectestimationtool.Model.Project;
import thestudiegruppe.projectestimationtool.Model.Status;
import thestudiegruppe.projectestimationtool.Model.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProjectRowMapper implements RowMapper<Project>{

        public Project mapRow(ResultSet rs, int rowNum) throws SQLException {

            User user = new User();
            user.setId(rs.getInt("user_id"));

            Project project = new Project(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("description"),
                    rs.getDate("date").toLocalDate(),
                    user,
                    Status.valueOf((rs.getString("status")))
            );
            return project;
        }
    }

