package dev.canlapan.daos;

import dev.canlapan.entities.Role;
import dev.canlapan.entities.Status;
import dev.canlapan.entities.User;
import dev.canlapan.utils.ConnectionUtil;

import java.sql.*;

public class UserDAOPostgres implements UserDAO {
    @Override
    public User createUser(User user) {
        try(Connection conn = ConnectionUtil.createConnection()){
            //insert into user values insert into app_user values (default, 'adamGator','pass123','COUNCIL');
            String sql = "insert into app_user values (default, ?, ?, ?, ?, ?)";

            PreparedStatement preparedStatement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            //field values set using front end
            preparedStatement.setString(1,user.getFname());
            preparedStatement.setString(2,user.getLname());
            preparedStatement.setString(3,user.getUsername());
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.setString(5, Role.COUNCIL.name());

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

    @Override
    public User getUserByUsername(String username) {
        try(Connection connection = ConnectionUtil.createConnection()){
            String sql = "select * from app_user where username = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,username);

            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();

            User user = new User();
            user.setUserId(resultSet.getInt("user_id"));
            user.setFname(resultSet.getString("fname"));
            user.setLname(resultSet.getString("lname"));
            user.setUsername(resultSet.getString("username"));
            user.setPassword(resultSet.getString("password"));
            user.setUserRole(Role.valueOf(resultSet.getString("role")));

            return user;

        }catch (SQLException exception){
            exception.printStackTrace();

        }
        return null;
    }
}
