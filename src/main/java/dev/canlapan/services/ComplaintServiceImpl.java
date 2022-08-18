package dev.canlapan.services;

import dev.canlapan.daos.ComplaintDAO;
import dev.canlapan.daos.ComplaintDAOPostgres;
import dev.canlapan.entities.Complaint;

import java.util.List;

public class ComplaintServiceImpl implements ComplaintService {

    private ComplaintDAO complaintDAO;

    public ComplaintServiceImpl(ComplaintDAO complaintDAO) { this.complaintDAO = complaintDAO;}


    @Override
    public Complaint registerComplaint(Complaint complaint) {
        if(complaint.getDescription().length() == 0){
            throw new RuntimeException("Please describe the event that took place. Max 200 characters");
        }

        Complaint savedComplaint = this.complaintDAO.createComplaint(complaint);
        return savedComplaint;
    }

    @Override
    public List<Complaint> getAllComplaints() {
        return this.complaintDAO.getAllComplaints();
    }

    public Complaint updateComplaint(int complaintId, Complaint complaint){
        return this.complaintDAO.updateComplaint(complaintId, complaint);
    }

    @Override
    public Complaint retrieveComplaintById(int complaintId) {
        return this.complaintDAO.getComplaintById(complaintId);
    }
}
