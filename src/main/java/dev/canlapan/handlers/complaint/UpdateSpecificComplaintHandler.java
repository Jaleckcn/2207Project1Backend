package dev.canlapan.handlers.complaint;

import com.google.gson.Gson;
import dev.canlapan.app.App;
import dev.canlapan.entities.Complaint;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;

public class UpdateSpecificComplaintHandler implements Handler {

    @Override
    public void handle(@NotNull Context ctx) throws Exception {
        int complaintId = Integer.parseInt(ctx.pathParam("complaint_id"));
        Complaint temp = App.complaintService.retrieveComplaintById(complaintId);

        if(temp == null){
            ctx.status(404);
            ctx.result("Complaint ID " + complaintId + " not found");
            return;
        }else{
            ctx.status();
            ctx.result("A complaint has been updated");
        }

        String complaintJSON = ctx.body();
        Gson gson = new Gson();
        Complaint complaint = gson.fromJson(complaintJSON, Complaint.class);
        Complaint updateComplaint = App.complaintService.updateComplaint(complaintId, complaint);
        String json = gson.toJson(updateComplaint);
        ctx.result(json);
    }
}
