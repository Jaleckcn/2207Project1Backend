package dev.canlapan.mocktests;

import dev.canlapan.daos.ComplaintDAO;
import dev.canlapan.daos.UserDAO;
import dev.canlapan.entities.Complaint;
import dev.canlapan.entities.Role;
import dev.canlapan.entities.Status;
import dev.canlapan.entities.User;
import dev.canlapan.services.ComplaintService;
import dev.canlapan.services.ComplaintServiceImpl;
import dev.canlapan.services.UserService;
import dev.canlapan.services.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

public class MockExampleTests {

    @Test
    void mock_1(){
        List<String> userList = Mockito.mock(List.class);
        Mockito.when(userList.get(21)).thenReturn("Jalec");
        System.out.println(userList.get(21));
    }

    @Test
    void register_complaint_must_have_valid_complaint(){
        ComplaintDAO complaintDAO = Mockito.mock(ComplaintDAO.class);
        Complaint complaint = new Complaint(0,"","","","", Status.UNREVIEWED,0);
        Mockito.when(complaintDAO.createComplaint(complaint)).thenReturn(complaint);
        ComplaintService complaintService = new ComplaintServiceImpl(complaintDAO);

        Assertions.assertThrows(RuntimeException.class, () ->{
           complaintService.registerComplaint(complaint);
        });
    }

    @Test
    void register_user_must_have_full_name(){
        UserDAO userDAO = Mockito.mock(UserDAO.class);
        User user = new User(1,"","","Jaleckcn","therightpass", Role.COUNCIL);
        Mockito.when(userDAO.createUser(user)).thenReturn(user);
        UserService userService = new UserServiceImpl(userDAO);
        System.out.println(user);

        Assertions.assertThrows(RuntimeException.class, () -> {
           userService.registerUser(user);
        });
    }

    @Test
    void sort_complaints_by_status(){
        ComplaintDAO complaintDAO = Mockito.mock(ComplaintDAO.class);
        List<Complaint> fakeComplaints = new ArrayList<>();
        fakeComplaints.add(new Complaint(0,"","","","Villains destroyed the park again",Status.UNREVIEWED,0));
        fakeComplaints.add(new Complaint(0,"Toshinori","Yagi","plusultra@ua.edu","My arch nemesis took out 5 city blocks",Status.HIGH,0));
        fakeComplaints.add(new Complaint(0,"Mitsuki","Bakugo","mitsuki.bakugo@yahoo.com","My son got into a fight with his friend again",Status.IGNORED,0));
        fakeComplaints.add(new Complaint(0,"","","kiminonawa@comixwave.com","Time Displacement. I feel like I'm missing something important ", Status.LOW,0));

        Mockito.when(complaintDAO.getAllComplaints()).thenReturn(fakeComplaints);

        ComplaintService complaintService = new ComplaintServiceImpl(complaintDAO);
        List<Complaint> sortedComplaints = complaintService.getStatus(Status.IGNORED);
        System.out.println(sortedComplaints);
        Assertions.assertEquals(1,sortedComplaints.size());
    }
}
