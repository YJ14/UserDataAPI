package com.jeries.reactive.user.data.api.service;

import com.jeries.reactive.user.data.api.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;


@Service
@Slf4j
public class UserService {

    private WebClient webClient;

    private static final String BASE_URL = "http://jsonplaceholder.typicode.com";
    private static final String USER_BY_ID = "/users/";


    public UserService() {
        this.webClient = WebClient.builder()
                .baseUrl(BASE_URL)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, "application/json")
                .build();
    }

    /**
     * Fetch User
     * @param id
     * @return
     */
    public Mono<User> getUser(String id) {

        try {
            Mono<User> user = this.webClient.get().uri(USER_BY_ID + id)
                    .retrieve()
                    .bodyToMono(User.class);
            return user;

        } catch (WebClientResponseException ex) {
            log.error("[getUser] Error Status code is : " +  ex.getRawStatusCode() + " - Exception message :" + ex.getResponseBodyAsString());
            throw ex;
        } catch (Exception ex) {
            log.error("Exception in [getUser] ", ex);
            throw ex;
        }
    }


}
