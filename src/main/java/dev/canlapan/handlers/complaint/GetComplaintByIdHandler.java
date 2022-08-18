package dev.canlapan.handlers.complaint;

import com.google.gson.Gson;
import dev.canlapan.app.App;
import dev.canlapan.entities.Complaint;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;

public class GetComplaintByIdHandler implements Handler {
    @Override
    public void handle(@NotNull Context ctx) throws Exception {
        int complaintId = Integer.parseInt(ctx.pathParam("complaint_id"));
        Complaint complaint = App.complaintService.retrieveComplaintById(complaintId);
        Gson gson = new Gson();
        try{
            String json = gson.toJson(complaint);

            //check to see if Complaint ID can be found, if not return a 404 status

            if(json.equals("null")){
                ctx.status(404);
                ctx.result("Complaint ID " + complaintId + " not found");
            }else{
                ctx.status(200);
                ctx.result(json);
            }
        }catch(RuntimeException e) {
            ctx.status(500);
            ctx.result("Error in retrieving complaint");
        }
    }
}
