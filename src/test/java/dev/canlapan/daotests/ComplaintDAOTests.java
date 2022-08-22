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
import java.util.List;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ComplaintDAOTests {

    static ComplaintDAO complaintDAO = new ComplaintDAOPostgres();

    @BeforeAll
    static void setup() {
        try (Connection conn = ConnectionUtil.createConnection()) {
            String sql = "create table complaint(\n" +
                    "\tcomplaint_id serial primary key, \n" +
                    "\tfname varchar(40), \n" +
                    "\tlname varchar(40), \n" +
                    "\temail varchar(40), \n" +
                    "\tdescription varchar(200) not null, \n" +
                    "\tstatus varchar(40), \n" +
                    "\tmeeting_id int default -1\n" +
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
        Complaint complaint = new Complaint(0,"Toshinori","Yagi","plusultra@ua.edu","Weird explodey kid keeps breaking the peace and yelling die all the time.", Status.UNREVIEWED,-1);
        Complaint savedComplaint = this.complaintDAO.createComplaint(complaint);
        Assertions.assertNotEquals(0,savedComplaint.getComplaintId());
    }

    @Test
    @Order(2)
    void get_complaint_by_id(){
        Complaint complaint = complaintDAO.getComplaintById(1);
        Assertions.assertEquals("UNREVIEWED",complaint.getStatus().name());
    }

    @Test
    @Order(3)
    void update_complaint_test(){
        Complaint complaint2 = new Complaint(1,"Toshinori","Yagi","plusultra@ua.edu","Weird explodey kid keeps breaking the peace and yelling die all the time.",Status.valueOf(Status.IGNORED.toString()),-1);
        complaintDAO.updateComplaint(1, complaint2);
        Complaint complaint = complaintDAO.getComplaintById(1);
        Assertions.assertEquals("plusultra@ua.edu",complaint.getEmail());
    }

    @Test
    @Order(4)
    void get_all_complaints_test(){
        Complaint complaint1 = new Complaint(0,"","","somethingsomething@yahoo.com","Weird explodey kid keeps breaking the peace and yelling die all the time.", Status.UNREVIEWED,-1);
        Complaint complaint2 = new Complaint(0,"","","oldschool@aol.com","Kids keep walking on my lawn", Status.UNREVIEWED,-1);
        Complaint complaint3 = new Complaint(0,"","","kiminonawa@comixwave.com","Time Displacement. I feel like I'm missing something important ", Status.UNREVIEWED,-1);
        Complaint complaint4 = new Complaint(0,"","","myemail@anon.net","I'm worried about the turtle flu", Status.UNREVIEWED,-1);

        complaintDAO.createComplaint(complaint1);
        complaintDAO.createComplaint(complaint2);
        complaintDAO.createComplaint(complaint3);
        complaintDAO.createComplaint(complaint4);

        List<Complaint> complaintList = complaintDAO.getAllComplaints();
        //created 4 here, but one was created from the first test for a total of 5 complaints
        Assertions.assertEquals(5,complaintList.size());

        System.out.println(complaint1);
        System.out.println(complaint2);
        System.out.println(complaint3);
        System.out.println(complaint4);
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
