package dev.canlapan.daos;

import dev.canlapan.entities.Complaint;
import dev.canlapan.entities.Status;
import dev.canlapan.utils.ConnectionUtil;

import javax.xml.transform.Result;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ComplaintDAOPostgres implements ComplaintDAO{
    @Override
    public Complaint createComplaint(Complaint complaint) {
        try(Connection conn = ConnectionUtil.createConnection()){
            //insert into complaint values (default, 'Jalec','Canlapan', 'Jaleckc@gmail.com','Villains destroyed the park again','UNREVIEWED',0);
            String sql = "insert into complaint values (default, ?, ?, ?, ?, ?, ?)";

            PreparedStatement preparedStatement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            //field values set using our front end
            preparedStatement.setString(1, complaint.getFname());
            preparedStatement.setString(2, complaint.getLname());
            preparedStatement.setString(3, complaint.getEmail());
            preparedStatement.setString(4, complaint.getDescription());
            preparedStatement.setString(5, Status.UNREVIEWED.name());
            preparedStatement.setInt(6, complaint.getMeetingId());

            preparedStatement.execute();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            rs.next();

            complaint.setComplaintId(rs.getInt("complaint_id"));

            return complaint;
        }catch(SQLException e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public List<Complaint> getAllComplaints() {
        try(Connection connection = ConnectionUtil.createConnection()){
            String sql = "select * from complaint";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            ResultSet rs = preparedStatement.executeQuery();
            List<Complaint> complaintList = new ArrayList<>();

            while(rs.next()){
                Complaint complaint = new Complaint();
                complaint.setComplaintId(rs.getInt("complaint_id"));
                complaint.setFname(rs.getString("fname"));
                complaint.setLname(rs.getString("lname"));
                complaint.setEmail(rs.getString("email"));
                complaint.setDescription(rs.getString("description"));
                complaint.setStatus(Status.valueOf(rs.getString("status")));
                complaint.setMeetingId(rs.getInt("meeting_id"));
                complaintList.add(complaint);
            }
            return complaintList;

        }catch(SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Complaint getComplaintById(int complaintId) {
        try(Connection connection = ConnectionUtil.createConnection()){
            String sql = "select * from complaint where complaint_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,complaintId);

            ResultSet rs = preparedStatement.executeQuery();
            rs.next();

            Complaint complaint = new Complaint();
            complaint.setComplaintId(rs.getInt("complaint_id"));
            complaint.setFname(rs.getString("fname"));
            complaint.setLname(rs.getString("lname"));
            complaint.setEmail(rs.getString("email"));
            complaint.setDescription(rs.getString("description"));
            complaint.setStatus(Status.valueOf(rs.getString("status")));
            complaint.setMeetingId(rs.getInt("meeting_id"));

            return complaint;

        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Complaint updateComplaint(int complaintId, Complaint complaint) {
        try(Connection conn = ConnectionUtil.createConnection()){

            //complaint id cannot be changed. But if a council member does schedule a meeting for a complaint
            //meeting id can be updated
            //all fields could technically be updated but the main ones are STATUS and MEETING_ID
            String sql = "update complaint set meeting_id = ?, status = ? where complaint_id = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);

            preparedStatement.setInt(1,complaint.getMeetingId());
            preparedStatement.setString(2,complaint.getStatus().name());
            preparedStatement.setInt(3,complaintId);


            preparedStatement.executeUpdate();
            return complaint;
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }

    }

}
