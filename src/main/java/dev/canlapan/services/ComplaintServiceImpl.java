package dev.canlapan.services;

import dev.canlapan.daos.ComplaintDAO;
import dev.canlapan.daos.ComplaintDAOPostgres;
import dev.canlapan.entities.Complaint;

public class ComplaintServiceImpl implements ComplaintService {

    private ComplaintDAO complaintDAO;

    public ComplaintServiceImpl(ComplaintDAO complaintDAO) { this.complaintDAO = complaintDAO;}


    @Override
    public Complaint registerComplaint(Complaint complaint) {

        Complaint savedComplaint = this.complaintDAO.createComplaint(complaint);
        return savedComplaint;
    }
}
