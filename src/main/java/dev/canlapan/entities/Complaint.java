package dev.canlapan.entities;

public class Complaint {

    private int complaintId;

    private String email;

    private String description;

    private Status status;

    private int meetingId; //meeting ID the complaint COULD be tied to

    public Complaint(int complaintId, String email, String description, Status status, int meetingId) {
        this.complaintId = complaintId;
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
                ", email='" + email + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", meetingId=" + meetingId +
                '}';
    }
}
