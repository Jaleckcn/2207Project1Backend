package dev.canlapan.daos;

import dev.canlapan.entities.Role;
import dev.canlapan.entities.User;
import dev.canlapan.utils.ConnectionUtil;

import java.sql.*;

public class UserDAOPostgres  implements UserDAO {
    @Override
    public User createUser(User user) {
        try(Connection conn = ConnectionUtil.createConnection()){
            //insert into user values insert into app_user values (default, 'adamGator','pass123','COUNCIL');
            String sql = "insert into app_user values (default, ?, ?, ?)";

            PreparedStatement preparedStatement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            //field values set using front end
            preparedStatement.setString(1,user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, Role.CONSTITUENT.name());

            preparedStatement.execute();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            rs.next();

            user.setUserId(rs.getInt("user_id"));

            return user;
        }catch(SQLException e) {
            e.printStackTrace();
            return null;

        }
    }
}
