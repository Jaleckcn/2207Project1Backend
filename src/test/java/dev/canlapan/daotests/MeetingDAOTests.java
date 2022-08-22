package dev.canlapan.daotests;


import dev.canlapan.daos.MeetingDAO;
import dev.canlapan.daos.MeetingDAOPostgres;
import dev.canlapan.entities.Meeting;
import dev.canlapan.utils.ConnectionUtil;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MeetingDAOTests {

    static MeetingDAO meetingDAO = new MeetingDAOPostgres();

    @BeforeAll
    static void setup() {
        try (Connection conn = ConnectionUtil.createConnection()) {
            String sql ="create table meeting(\n" +
                        "\tmeeting_id serial primary key, \n" +
                        "\taddress varchar(200), \n" +
                        "\ttime int, \n" +
                        "\tsummary varchar(200) \n" +
                        ");";


            //setting our default meeting, -1 means no meeting assigned to a complaint
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
    void create_meeting_test(){
        Meeting meeting = new Meeting(0,"UA Town Hall", 1800, "Forum to address issues petitioned by the community");
        Meeting savedMeeting = this.meetingDAO.createMeeting(meeting);
        Assertions.assertEquals(1800,savedMeeting.getTime());
        System.out.println(meeting);
    }

    @Test
    @Order(2)
    void get_all_meetings_test(){
        Meeting meeting1 = new Meeting(0,"UA Town Hall", 800, "Forum addressing complaints 1-10");
        Meeting meeting2 = new Meeting(0,"UA Town Hall", 1200, "Forum addressing complaints 11,12,14 and 15");
        Meeting meeting3 = new Meeting(0,"UA Town Hall", 1300, "Addressing complaints TBD");
        Meeting meeting4 = new Meeting(0,"UA Town Hall", 1930, "Open floor for constituents to directly address the council");

        meetingDAO.createMeeting(meeting1);
        meetingDAO.createMeeting(meeting2);
        meetingDAO.createMeeting(meeting3);
        meetingDAO.createMeeting(meeting4);

        List<Meeting> meetingList = meetingDAO.getAllMeetings();
        //we have one meeting as a placeholder plus one created from the test above 2+4=6 records
        Assertions.assertEquals(6,meetingList.size());
    }

    @AfterAll // Runs after the last tests finishes
    //If you don't want the table to be deleted, just comment this section out
    static void teardown() {
        try (Connection connection = ConnectionUtil.createConnection()) {
            String sql = "drop table meeting";
            Statement statement = connection.createStatement();
            statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
