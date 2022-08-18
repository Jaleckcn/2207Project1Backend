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
            //insert into complaint values (default, 'example123@gmail.com', 'Shadow monsters rummaging in my backyard', 'UNREVIEWED',-1);

            String sql = "insert into complaint values (default, ?, ?, ?, default)";

            PreparedStatement preparedStatement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            //field values set using our front end
            preparedStatement.setString(1, complaint.getEmail());
            preparedStatement.setString(2, complaint.getDescription());
            preparedStatement.setString(3, Status.UNREVIEWED.name());

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
            String sql = "update complaint set complaint_id = ?, email = ?, description = ?, status = ?, meeting_id = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);

            preparedStatement.setInt(1,complaint.getComplaintId());
            preparedStatement.setString(2, complaint.getEmail());
            preparedStatement.setString(3, complaint.getDescription());
            preparedStatement.setString(4,complaint.getStatus().name());
            preparedStatement.setInt(5,complaint.getMeetingId());


            return complaint;
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }

    }

}
