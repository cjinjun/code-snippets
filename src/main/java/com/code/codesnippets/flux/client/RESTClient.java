package com.code.codesnippets.flux.client;


import com.code.codesnippets.flux.pojo.User;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

public class RESTClient {
    public static void main(final String[] args) {
        User user = new User();
        user.setId("1");
        user.setName("Test");
        user.setAge(15);
        final WebClient client = WebClient.create("http://localhost:8080/user");
        final Flux<User> createdUser = client.post()
                .uri("")
                .accept(MediaType.APPLICATION_JSON)
                .body(Flux.just(user), User.class)
                .exchange()
                .flatMapMany(response -> response.bodyToFlux(User.class));
        createdUser.doOnNext(System.out::println);

    }
}