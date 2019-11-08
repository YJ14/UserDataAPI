package com.jeries.reactive.user.data.api.controller;

import com.jeries.reactive.user.data.api.model.User;
import com.jeries.reactive.user.data.api.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@Slf4j
public class UserController {

    @Autowired
    UserService userService;

    private final String USER_ID = "/user/{id}";


    @RequestMapping(value=USER_ID, method = RequestMethod.GET)
    @CrossOrigin
    public Mono<User> getUserById(@PathVariable(value = "id") String id) {

        Mono<User> user = userService.getUser(id);

        return user;
    }
}
