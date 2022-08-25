package dev.canlapan.entities;

public class Meeting {

    private int meetingId;

    private String address; //address meeting will be held

    private long time;//date and time meeting will be held

    private String summary;  //description of what meeting will cover

    public Meeting(int meetingId, String address, long time, String summary) {
        this.meetingId = meetingId;
        this.address = address;
        this.time = time;
        this.summary = summary;
    }

    public Meeting() {

    }

    public int getMeetingId() {
        return meetingId;
    }

    public void setMeetingId(int meetingId) {
        this.meetingId = meetingId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    @Override
    public String toString() {
        return "Meeting{" +
                "meetingId=" + meetingId +
                ", address='" + address + '\'' +
                ", time=" + time +
                ", summary='" + summary + '\'' +
                '}';
    }
}
