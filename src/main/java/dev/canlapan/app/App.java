package dev.canlapan.app;

import dev.canlapan.daos.ComplaintDAOPostgres;
import dev.canlapan.daos.MeetingDAOPostgres;
import dev.canlapan.daos.UserDAOPostgres;
import dev.canlapan.entities.Complaint;
import dev.canlapan.entities.Meeting;
import dev.canlapan.entities.User;
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
        //creating a new user. All registered users will have Constituent status
        //But can be updated to COUNCIL status if member of council
        app.post("/user",createUserHandler);

        app.start();
    }
}
