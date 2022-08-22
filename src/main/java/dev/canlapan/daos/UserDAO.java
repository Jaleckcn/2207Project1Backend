package dev.canlapan.daos;

import dev.canlapan.entities.User;

public interface UserDAO {
    User createUser(User user);

    User getUserByUsername(String username);


}
