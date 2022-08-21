package dev.canlapan.daos;

import dev.canlapan.entities.Meeting;
import dev.canlapan.utils.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MeetingDAOPostgres implements MeetingDAO{
    @Override
    public Meeting createMeeting(Meeting meeting) {
        try(Connection conn = ConnectionUtil.createConnection()){
            //insert into meeting values (-1,'NO MEETING ASSIGNED (address)',0,'NOT A REAL MEETING (summary)');

            String sql = "insert into meeting values (default, ?, ?, ?)";

            PreparedStatement preparedStatement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            //field values set using our front end
            preparedStatement.setString(1, meeting.getAddress());
            preparedStatement.setInt(2, meeting.getTime());
            preparedStatement.setString(3, meeting.getSummary());

            preparedStatement.execute();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            rs.next();

            meeting.setMeetingId(rs.getInt("meeting_id"));
            return meeting;

        }catch(SQLException e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public List<Meeting> getAllMeetings() {
        try(Connection connection = ConnectionUtil.createConnection()){
            String sql = "select * from meeting";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            ResultSet rs = preparedStatement.executeQuery();
            List<Meeting> meetingList = new ArrayList<>();

            while(rs.next()){
                Meeting meeting = new Meeting();
                meeting.setMeetingId(rs.getInt("meeting_id"));
                meeting.setAddress(rs.getString("address"));
                meeting.setTime(rs.getInt("time"));
                meeting.setSummary(rs.getString("summary"));
                meetingList.add(meeting);

            }
            return meetingList;
        }catch(SQLException e){
            e.printStackTrace();
            return null;
        }
    }

}
