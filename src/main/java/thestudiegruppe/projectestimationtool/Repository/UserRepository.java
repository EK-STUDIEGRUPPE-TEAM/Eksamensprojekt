package thestudiegruppe.projectestimationtool.Repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import thestudiegruppe.projectestimationtool.Mapper.UserRowMapper;
import thestudiegruppe.projectestimationtool.Model.User;

import java.util.List;

@Repository
public class UserRepository {

    private final JdbcTemplate jdbcTemplate;


    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public List<User> getAllUsers() {
        String sql = "SELECT * FROM Users";
        return jdbcTemplate.query(sql, new UserRowMapper());
    }

    public void signUp(User user) {
        String sql = "INSERT INTO Users(name, email, password) VALUES(?,?,?)";

        jdbcTemplate.update(sql, user.getName(), user.getEmail(), user.getPassword());
    }

    public User findUserForLogIn(String email, String password){
        String sql = "SELECT * FROM Users WHERE email = ? AND password = ?";

        return jdbcTemplate.queryForObject(sql, new UserRowMapper(), email, password);
    }

    public void delete(int id) {
        String sql = "DELETE FROM Users WHERE user_id = ?";

        jdbcTemplate.update(sql, id);

    }

    public User findUserById(int id){
        String sql = "SELECT * FROM Users WHERE user_id = ?";

        return jdbcTemplate.queryForObject(sql, new UserRowMapper(), id);
    }

    public void update(User user){
        String sql = "UPDATE Users SET name = ? WHERE user_id = ?";

        jdbcTemplate.update(sql, user.getName(), user.getId());
    }
}
