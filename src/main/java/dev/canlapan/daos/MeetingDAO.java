package dev.canlapan.daos;

import dev.canlapan.entities.Meeting;

import java.util.List;

public interface MeetingDAO {

    //Create
    Meeting createMeeting(Meeting meeting);

    //Read
    List<Meeting> getAllMeetings();


}
