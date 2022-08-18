package dev.canlapan.daos;

import dev.canlapan.entities.Complaint;

import java.util.List;

public interface ComplaintDAO {

    //Create
    Complaint createComplaint(Complaint complaint);

    //Read
    List<Complaint> getAllComplaints();
    Complaint getComplaintById(int complaintId);
    //Update
    Complaint updateComplaint(int complaintId, Complaint complaint);

    //Delete?
}
