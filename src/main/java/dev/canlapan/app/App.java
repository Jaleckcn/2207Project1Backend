package dev.canlapan.app;

import com.google.gson.Gson;
import dev.canlapan.daos.ComplaintDAOPostgres;
import dev.canlapan.daos.MeetingDAOPostgres;
import dev.canlapan.daos.UserDAOPostgres;
import dev.canlapan.dtos.LoginCredentials;
import dev.canlapan.entities.Complaint;
import dev.canlapan.entities.Meeting;
import dev.canlapan.entities.User;
import dev.canlapan.exceptions.NoEmployeeFoundException;
import dev.canlapan.exceptions.PasswordMismatchException;
import dev.canlapan.handlers.complaint.CreateComplaintHandler;
import dev.canlapan.handlers.complaint.GetAllComplaintsHandler;
import dev.canlapan.handlers.complaint.GetComplaintByIdHandler;
import dev.canlapan.handlers.complaint.UpdateSpecificComplaintHandler;
import dev.canlapan.handlers.meeting.CreateMeetingHandler;
import dev.canlapan.handlers.meeting.GetAllMeetingsHandler;
import dev.canlapan.handlers.user.CreateUserHandler;
import dev.canlapan.services.*;
import io.javalin.Javalin;
import io.javalin.http.Handler;

import java.util.ArrayList;
import java.util.List;

public class App {

    public static List<Complaint> complaint = new ArrayList<>();
    public static List<Meeting> meeting = new ArrayList<>();
    public static List<User> user = new ArrayList<>();

    public static ComplaintService complaintService = new ComplaintServiceImpl(new ComplaintDAOPostgres());
    public static MeetingService meetingService = new MeetingServiceImpl(new MeetingDAOPostgres());
    public static UserService userService = new UserServiceImpl(new UserDAOPostgres());

    public static LoginService loginService = new LoginServiceImpl(new UserDAOPostgres());

    public static void main(String[] args) {
        Javalin app = Javalin.create( config-> {
            config.enableDevLogging();
            config.enableCorsForAllOrigins();
        });

        CreateComplaintHandler createComplaintHandler = new CreateComplaintHandler();
        GetAllComplaintsHandler getAllComplaintsHandler = new GetAllComplaintsHandler();
        GetComplaintByIdHandler getComplaintByIdHandler = new GetComplaintByIdHandler();
        UpdateSpecificComplaintHandler updateSpecificComplaintHandler = new UpdateSpecificComplaintHandler();

        //Creating a complaint
        app.post("/complaints",createComplaintHandler);
        //Getting a list of all complaints
        app.get("/complaints",getAllComplaintsHandler);
        //Getting a specific complaint by ID
        app.get("/complaints/{complaintId}", getComplaintByIdHandler);
        //update a complaint record
        app.put("/complaints/{complaintId}", updateSpecificComplaintHandler);

        CreateMeetingHandler createMeetingHandler = new CreateMeetingHandler();
        GetAllMeetingsHandler getAllMeetingsHandler = new GetAllMeetingsHandler();

        //Creating a meeting
        app.post("/meetings",createMeetingHandler);
        //Getting a list of all meetings
        app.get("/meetings",getAllMeetingsHandler);

        CreateUserHandler createUserHandler = new CreateUserHandler();
        //creating a new user. All registered users will have COUNCIL status for now
        //But perhaps in the future, we could have someone verify user before activating account
        app.post("/user",createUserHandler);

        app.post("/login",ctx -> {
           String body = ctx.body();
           Gson gson = new Gson();

           LoginCredentials credentials = gson.fromJson(body, LoginCredentials.class);

           User user = loginService.validateUser(credentials.getUsername(), credentials.getPassword());
           String userJSON = gson.toJson(user);
           ctx.result(userJSON);

        });
        //Exceptions implemented to check if valid username or password
        app.exception(PasswordMismatchException.class,(exception, ctx) -> {
            ctx.status(422);
            ctx.result("Password did not match");
        });
        app.exception(NoEmployeeFoundException.class,(exception, ctx) -> {
            ctx.status(404);
            ctx.result("User not found");
        });

        Handler updateComplaintMeetingIdHandler = ctx -> {
            int complaintId = Integer.parseInt(ctx.pathParam("complaintId"));
            int meetingId = Integer.parseInt(ctx.pathParam("meetingId"));

            Complaint tempComplaint = App.complaintService.retrieveComplaintById(complaintId);
            tempComplaint.setMeetingId(meetingId);
            Complaint complaint1 = App.complaintService.updateComplaint(complaintId,tempComplaint);
            Gson gson = new Gson();
            String json = gson.toJson(complaint1);
            ctx.result(json);
        };

        app.put("/complaints/{complaintId}/{meetingId}",updateComplaintMeetingIdHandler);


        app.start();
    }
}
