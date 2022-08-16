package dev.canlapan.handlers.complaint;

import com.google.gson.Gson;
import dev.canlapan.app.App;
import dev.canlapan.entities.Complaint;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;

public class CreateComplaintHandler implements Handler {

    @Override
    public void handle(@NotNull Context ctx) throws Exception{
        String json = ctx.body();
        Gson gson = new Gson();
        Complaint complaint = gson.fromJson(json, Complaint.class);
        Complaint registerComplaint = App.complaintService.registerComplaint(complaint);
        String complaintJson = gson.toJson(registerComplaint);
        App.complaint.add(complaint);
        ctx.status(201);
        ctx.result(complaintJson);
    }
}
