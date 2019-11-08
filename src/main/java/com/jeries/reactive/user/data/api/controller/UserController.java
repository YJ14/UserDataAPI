package com.jeries.reactive.user.data.api.controller;

import com.jeries.reactive.user.data.api.model.Comment;
import com.jeries.reactive.user.data.api.model.User;
import com.jeries.reactive.user.data.api.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@Slf4j
public class UserController {

    @Autowired
    UserService userService;

    private final String USER_ID = "/user/{id}";

    /**
     * Mono and Flux (WebFlux Framework) is used to asynchronously fetch data from the user/posts API.
     *
     * The time that takes to finish the two asynchronous requests is determined by the longest request
     * and not by the combined time of both requests.
     * @param id
     * @return
     */
    @RequestMapping(value=USER_ID, method = RequestMethod.GET)
    @CrossOrigin
    public Mono<ResponseEntity<User>> getUser(@PathVariable(value = "id") String id) {

        Mono<User> user = userService.getUser(id);
        Flux<Comment> commentsByUser = userService.getCommentsByUser(id);

        // Combine Mono (user) and Flux (comments) in a functional style
        Mono<User> userInfo = user.flatMap(u -> commentsByUser.collectList().map(comments -> new User(u, comments)));

        // Wrap Mono in ResponseEntity to handle HTTP Responses / Error Responses
        // Error Responses can be personalised by creating a custom class that extends the Response Class
        Mono<ResponseEntity<User>> responseEntityMono = userInfo
                .map(ResponseEntity::ok)
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));

        return responseEntityMono;
    }

}
