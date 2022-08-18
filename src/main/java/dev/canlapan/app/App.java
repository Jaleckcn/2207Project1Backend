package dev.canlapan.app;

import dev.canlapan.daos.ComplaintDAOPostgres;
import dev.canlapan.entities.Complaint;
import dev.canlapan.entities.Meeting;
import dev.canlapan.entities.User;
import dev.canlapan.handlers.complaint.CreateComplaintHandler;
import dev.canlapan.handlers.complaint.GetAllComplaintsHandler;
import dev.canlapan.services.ComplaintService;
import dev.canlapan.services.ComplaintServiceImpl;
import io.javalin.Javalin;
import io.javalin.http.Handler;

import java.util.ArrayList;
import java.util.List;

public class App {

    public static List<Complaint> complaint = new ArrayList<>();
    public static List<Meeting> meeting = new ArrayList<>();
    public static List<User> user = new ArrayList<>();

    public static ComplaintService complaintService = new ComplaintServiceImpl(new ComplaintDAOPostgres()) {
    };

    public static void main(String[] args) {
        Javalin app = Javalin.create( config-> {
            config.enableDevLogging();
            config.enableCorsForAllOrigins();
        });

        CreateComplaintHandler createComplaintHandler = new CreateComplaintHandler();
        GetAllComplaintsHandler getAllComplaintsHandler = new GetAllComplaintsHandler();

        //Creating a complaint
        app.post("/complaint",createComplaintHandler);
        //Getting all complaints
        app.get("/complaints",getAllComplaintsHandler);

    }
}
