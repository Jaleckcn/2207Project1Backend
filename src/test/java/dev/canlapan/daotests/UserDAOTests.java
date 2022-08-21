package dev.canlapan.daotests;

import dev.canlapan.daos.UserDAO;
import dev.canlapan.daos.UserDAOPostgres;
import dev.canlapan.entities.Role;
import dev.canlapan.entities.User;
import dev.canlapan.utils.ConnectionUtil;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserDAOTests {

    static UserDAO userDAO = new UserDAOPostgres();

    @BeforeAll
    static void setup() {
        try (Connection conn = ConnectionUtil.createConnection()) {
            String sql = "create table app_user(\n" +
                         "\tuser_id serial primary key, \n" +
                         "\tusername varchar(40), \n" +
                         "\tpassword varchar(40), \n" +
                         "\trole varchar(30) \n" +
                         ");";

            Statement statement = conn.createStatement();
            statement.execute(sql);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    @Order(1)
    void create_user_test(){
        User user = new User(0,"Jaleckcn","therightpassword", Role.CONSTITUENT);
        User savedUser = this.userDAO.createUser(user);
        Assertions.assertNotEquals(0,savedUser.getUserId());
    }

//    @AfterAll // Runs after the last tests finishes
//    //If you don't want the table to be deleted, just comment this section out
//    static void teardown() {
//        try (Connection connection = ConnectionUtil.createConnection()) {
//            String sql = "drop table app_user";
//            Statement statement = connection.createStatement();
//            statement.execute(sql);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
}

