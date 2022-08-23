package dev.canlapan.services;

import dev.canlapan.entities.Meeting;
import dev.canlapan.daos.MeetingDAO;

import java.util.List;

public class MeetingServiceImpl implements MeetingService{

    private MeetingDAO meetingDAO;
    public MeetingServiceImpl(MeetingDAO meetingDAO) {this.meetingDAO = meetingDAO;}

    @Override
    public Meeting registerMeeting(Meeting meeting) {
        if(meeting.getAddress().length()==0){
            throw new RuntimeException("Please set an address for the meeting OR write TBA if no address decided");
        }
        if(meeting.getTime()< System.currentTimeMillis()/1000){
            throw new RuntimeException("Date cannot be before current date");
        }

        Meeting savedMeeting = this.meetingDAO.createMeeting(meeting);
        return savedMeeting;
    }

    @Override
    public List<Meeting> getAllMeetings() {
        return this.meetingDAO.getAllMeetings();
    }
}
