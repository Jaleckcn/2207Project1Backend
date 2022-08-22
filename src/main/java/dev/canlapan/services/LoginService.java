package dev.canlapan.services;

import dev.canlapan.entities.User;

public interface LoginService {

    User validateUser(String username, String password);
}
