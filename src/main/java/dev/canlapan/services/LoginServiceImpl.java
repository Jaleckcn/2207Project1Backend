package dev.canlapan.services;

import dev.canlapan.daos.UserDAO;
import dev.canlapan.entities.User;
import dev.canlapan.exceptions.NoEmployeeFoundException;
import dev.canlapan.exceptions.PasswordMismatchException;

public class LoginServiceImpl implements LoginService{

    private UserDAO userDAO;

    public LoginServiceImpl(UserDAO userDAO){
        this.userDAO = userDAO;
    }
    @Override
    public User validateUser(String username, String password) {
        User user = this.userDAO.getUserByUsername(username);
        if(user==null){
            throw new NoEmployeeFoundException("No user found");
        }
        if(!user.getPassword().equals(password)){
            throw new PasswordMismatchException("password does not match");

        }

        return user;
    }
}
