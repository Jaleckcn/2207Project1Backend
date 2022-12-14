package dev.canlapan.services;

import dev.canlapan.entities.User;
import dev.canlapan.daos.UserDAO;

public class UserServiceImpl implements UserService{

    private UserDAO userDAO;

    public UserServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public User registerUser(User user) {
        if(user.getFname().length()==0){
            throw new RuntimeException("Please enter your first name");
        }
        if(user.getLname().length()==0){
            throw new RuntimeException("Please enter your last name");
        }
        if(user.getUsername().length() < 5){
            throw new RuntimeException("Username should be longer than 5 characters");
        }
        if(user.getPassword().length() < 7){
            throw new RuntimeException("Password is recommended to be at least 8 characters long");
        }

        User savedUser = this.userDAO.createUser(user);
        return savedUser;
    }
}
