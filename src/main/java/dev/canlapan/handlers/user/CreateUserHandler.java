package dev.canlapan.handlers.user;

import com.google.gson.Gson;
import dev.canlapan.app.App;
import dev.canlapan.entities.User;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;

public class CreateUserHandler implements Handler {
    @Override
    public void handle(@NotNull Context ctx) throws Exception {
        String json = ctx.body();
        Gson gson = new Gson();
        User user = gson.fromJson(json,User.class);
        User registerUser = App.userService.registerUser(user);
        String userJson = gson.toJson(registerUser);
        App.user.add(user);
        ctx.status(201);
        ctx.result(userJson);
    }
}
