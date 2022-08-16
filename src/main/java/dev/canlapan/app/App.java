package dev.canlapan.app;

import dev.canlapan.daos.ComplaintDAOPostgres;
import dev.canlapan.entities.Complaint;
import dev.canlapan.entities.Meeting;
import dev.canlapan.entities.User;
import dev.canlapan.handlers.complaint.CreateComplaintHandler;
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

        //Creating a complaint
        CreateComplaintHandler createComplaintHandler = new CreateComplaintHandler();
        app.post("/complaint",createComplaintHandler);
    }
}
