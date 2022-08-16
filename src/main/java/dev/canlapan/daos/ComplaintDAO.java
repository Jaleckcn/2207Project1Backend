package dev.canlapan.daos;

import dev.canlapan.entities.Complaint;

public interface ComplaintDAO {

    //Create
    Complaint createComplaint(Complaint complaint);

    //Read
    Complaint getComplaintById(int complaintId);

    //Update
    Complaint updateComplaint(int complaintId, Complaint complaint);

    //Delete?
    boolean deleteComplaintById(int complaintId);
}
