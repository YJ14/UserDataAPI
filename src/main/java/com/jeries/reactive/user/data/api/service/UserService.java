package com.jeries.reactive.user.data.api.service;

import com.jeries.reactive.user.data.api.model.Comment;
import com.jeries.reactive.user.data.api.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Service
@Slf4j
public class UserService {

    private WebClient webClient;

    private static final String BASE_URL = "http://jsonplaceholder.typicode.com";
    private static final String USER_BY_ID = "/users/";
    private static final String COMMENTS_BY_ID = "posts?userId=";


    /**
     *  WebClient will replace soon the RestTemplate
     *  WebClient is thread-safe and reactive (asynchronous and non-blocking).
     */
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
                    .onStatus(HttpStatus::is4xxClientError, clientResponse -> handleErrorResponse(clientResponse))
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

    /**
     * Fetch User Comments
     * @param id
     * @return
     */
    public Flux<Comment> getCommentsByUser(String id) {

        try {
            Flux<Comment> comments = this.webClient.get().uri(COMMENTS_BY_ID + id)
                    .retrieve()
                    .onStatus(HttpStatus::is4xxClientError, clientResponse -> handleErrorResponse(clientResponse))
                    .bodyToFlux(Comment.class);
            return comments;

        } catch (WebClientResponseException ex) {
            log.error("[getCommentsByUser] Error Status Code  " +  ex.getRawStatusCode() + " - Exception message : " + ex.getResponseBodyAsString());
            throw ex;
        } catch (Exception ex) {
            log.error("Exception in [getCommentsByUser] ", ex);
            throw ex;
        }
    }


    public Mono<RuntimeException> handleErrorResponse(ClientResponse clientResponse) {

        Mono<String> error = clientResponse.bodyToMono(String.class);
        return error.flatMap((message) -> {
            log.error("Error Status Code : " + clientResponse.rawStatusCode() + " - Exception message : " + message);
            throw new RuntimeException(message);
        });

    }

}
