package dev.canlapan.daotests;

import dev.canlapan.daos.ComplaintDAO;
import dev.canlapan.daos.ComplaintDAOPostgres;
import dev.canlapan.entities.Complaint;
import dev.canlapan.entities.Status;
import dev.canlapan.utils.ConnectionUtil;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ComplaintDAOTests {

    static ComplaintDAO complaintDAO = new ComplaintDAOPostgres();

    @BeforeAll
    static void setup() {
        try (Connection conn = ConnectionUtil.createConnection()) {
            String sql = "create table complaint(\n" +
                    "\tcomplaint_id serial primary key, \n" +
                    "\temail varchar(40), \n" +
                    "\tdescription varchar(200) not null, \n" +
                    "\tstatus varchar(40), \n" +
                    "\tmeeting_id int references meeting(meeting_id) default -1\n" +
                    ");";

            String sql2 = "insert into meeting values (-1,'NO MEETING ASSIGNED',0,'NOT A REAL MEETING');";

            Statement statement = conn.createStatement();
            statement.execute(sql);
            statement.execute(sql2);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    @Order(1)
    void create_complaint_test(){
        //Complaint id should be auto-incrementing
        Complaint complaint = new Complaint(0,"example123@gmail.com","Weird explodey kid keeps breaking the peace and yelling die all the time.", Status.UNREVIEWED,0);
        Complaint savedComplaint = this.complaintDAO.createComplaint(complaint);
        Assertions.assertNotEquals(0,savedComplaint.getComplaintId());
    }

    @AfterAll // Runs after the last tests finishes
    //If you don't want the table to be deleted, just comment this section out
    static void teardown() {
        try (Connection connection = ConnectionUtil.createConnection()) {
            String sql = "drop table complaint";
            Statement statement = connection.createStatement();
            statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
