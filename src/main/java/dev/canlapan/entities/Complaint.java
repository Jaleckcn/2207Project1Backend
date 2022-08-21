package dev.canlapan.entities;

public class Complaint {

    private int complaintId;

    private String fname;
    private String lname;
    private String email;

    private String description;
    private Status status;

    private int meetingId;

    public Complaint(int complaintId, String fname, String lname, String email, String description, Status status, int meetingId) {
        this.complaintId = complaintId;
        this.fname = fname;
        this.lname = lname;
        this.email = email;
        this.description = description;
        this.status = status;
        this.meetingId = meetingId;
    }

    public Complaint() {

    }

    public int getComplaintId() {
        return complaintId;
    }

    public void setComplaintId(int complaintId) {
        this.complaintId = complaintId;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public int getMeetingId() {
        return meetingId;
    }

    public void setMeetingId(int meetingId) {
        this.meetingId = meetingId;
    }

    @Override
    public String toString() {
        return "Complaint{" +
                "complaintId=" + complaintId +
                ", fname='" + fname + '\'' +
                ", lname='" + lname + '\'' +
                ", email='" + email + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", meetingId=" + meetingId +
                '}';
    }
}
