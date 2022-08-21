package dev.canlapan.services;

import dev.canlapan.daos.MeetingDAO;
import dev.canlapan.daos.MeetingDAOPostgres;
import dev.canlapan.entities.Meeting;

import java.util.List;

public class MeetingServiceImpl implements MeetingService{

    private MeetingDAO meetingDAO;
    public MeetingServiceImpl(MeetingDAO meetingDAO) {this.meetingDAO = meetingDAO;}

    @Override
    public Meeting registerMeeting(Meeting meeting) {
        if(meeting.getAddress().length()==0){
            throw new RuntimeException("Please set an address for the meeting OR write TBA if no address decided");
        }

        Meeting savedMeeting = this.meetingDAO.createMeeting(meeting);
        return savedMeeting;
    }

    @Override
    public List<Meeting> getAllMeetings() {
        return this.meetingDAO.getAllMeetings();
    }
}
