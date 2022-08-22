package dev.canlapan.services;

import dev.canlapan.entities.Meeting;

import java.util.List;

public interface MeetingService {

    Meeting registerMeeting(Meeting meeting);

    List<Meeting> getAllMeetings();
}
