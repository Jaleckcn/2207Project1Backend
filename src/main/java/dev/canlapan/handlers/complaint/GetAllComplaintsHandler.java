package dev.canlapan.handlers.complaint;

import com.google.gson.Gson;
import dev.canlapan.app.App;
import dev.canlapan.entities.Complaint;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Collectors;

public class GetAllComplaintsHandler implements Handler {
    @Override
    public void handle(@NotNull Context ctx) throws Exception {
        List<Complaint> complaints = App.complaintService.getAllComplaints();
        String input = ctx.queryParam("meetingId");
        if(input != null) {
            int meetingId = Integer.parseInt(input);
            if (meetingId > 0){
                complaints = complaints.stream().filter(complaint -> complaint.getMeetingId()==meetingId).collect(Collectors.toList());
            }
        }
        Gson gson = new Gson();
        String json = gson.toJson(complaints);
        ctx.result(json);
        ctx.status(200);
    }


}
