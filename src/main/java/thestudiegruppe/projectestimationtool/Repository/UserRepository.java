package thestudiegruppe.projectestimationtool.Repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import thestudiegruppe.projectestimationtool.Model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepository {

     private final JdbcTemplate jdbcTemplate;


    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public static class UserRowMapper implements RowMapper<User>{

        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException{

            User user = new User(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getString("password"),
                    new ArrayList<>()
            );

            return user;
        }

    }

    public void createUser(User user){
        String sql = "INSERT INTO User(name, email, password) VALUES(?,?,?)";
        jdbcTemplate.update(sql, user.getName(), user.getEmail(), user.getPassword());
    }

    public void delete(int id){
        String sql = "DELETE FROM User WHERE user_id = ?";

        jdbcTemplate.update(sql, id);

    }

    public List<User> findUserById(int id){
        String sql = "SELECT * FROM User WHERE user_id = ?";

        return jdbcTemplate.query(sql, new UserRowMapper());
    }

    public void update(User user){
        String sql = "UPDATE User SET name = ? WHERE user_id = ?";

        jdbcTemplate.update(sql, user.getName(), user.getId());
    }
}
