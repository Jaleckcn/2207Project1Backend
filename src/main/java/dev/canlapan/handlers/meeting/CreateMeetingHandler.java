package dev.canlapan.handlers.meeting;

import com.google.gson.Gson;
import dev.canlapan.app.App;
import dev.canlapan.entities.Meeting;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;

public class CreateMeetingHandler implements Handler {
    @Override
    public void handle(@NotNull Context ctx) throws Exception {
        String json = ctx.body();
        Gson gson = new Gson();
        Meeting meeting = gson.fromJson(json, Meeting.class);
        Meeting registerMeeting = App.meetingService.registerMeeting(meeting);
        String meetingJson = gson.toJson(registerMeeting);
        App.meeting.add(meeting);
        ctx.status(201);
        ctx.result(meetingJson);
    }
}
