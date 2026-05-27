    package thestudiegruppe.projectestimationtool.Mapper;

    import org.springframework.jdbc.core.RowMapper;
    import thestudiegruppe.projectestimationtool.Model.Project;
    import thestudiegruppe.projectestimationtool.Model.Status;
    import thestudiegruppe.projectestimationtool.Model.User;

    import java.sql.ResultSet;
    import java.sql.SQLException;
    import java.time.LocalDate;

    public class ProjectRowMapper implements RowMapper<Project> {

        public Project mapRow(ResultSet rs, int rowNum) throws SQLException {

            return new Project(
                    rs.getInt("project_id"),
                    rs.getString("name"),
                    rs.getString("description"),
                    rs.getDate("date").toLocalDate(),
                    rs.getDate("deadline").toLocalDate(),
                    rs.getDouble("budget"),
                    Status.valueOf((rs.getString("status"))),
                    rs.getInt("user_id")
            );
        }
    }

