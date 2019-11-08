package com.jeries.reactive.user.data.api.service;

import com.jeries.reactive.user.data.api.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


@Service
@Slf4j
public class UserService {

    private WebClient webClient;

    private static final String BASE_URL = "http://jsonplaceholder.typicode.com";


    public UserService() {
        this.webClient = WebClient.builder()
                .baseUrl(BASE_URL)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, "application/json")
                .build();
    }

    public Mono<User> getUser(String id) {

        Mono<User> user = this.webClient.get().uri("/users/" + id)
                .retrieve()
                .bodyToMono(User.class);

        return user;
    }


}
