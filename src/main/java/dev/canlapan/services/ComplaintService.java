package dev.canlapan.services;

import dev.canlapan.entities.Complaint;

import java.util.List;

public interface ComplaintService {
    Complaint registerComplaint(Complaint complaint);

    List<Complaint> getAllComplaints();

    Complaint updateComplaint(int complaintId, Complaint complaint);

    Complaint retrieveComplaintById(int complaintId);
}
