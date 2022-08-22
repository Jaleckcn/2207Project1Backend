package dev.canlapan.services;

import dev.canlapan.entities.Complaint;
import dev.canlapan.entities.Status;

import java.util.List;

public interface ComplaintService {
    Complaint registerComplaint(Complaint complaint);

    List<Complaint> getAllComplaints();
    List<Complaint> getStatus(Status status);

    Complaint updateComplaint(int complaintId, Complaint complaint);

    Complaint retrieveComplaintById(int complaintId);
}
