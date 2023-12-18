package com.petdiary.clients.petdiaryauthclient.service;

import com.petdiary.clients.petdiaryauthclient.dto.UserClientRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class UserClientSvc {
    private final WebClient webClient;

    @Autowired
    public UserClientSvc(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:8081").build();
    }

    public Mono<UserClientRes.MyDto> getUserData() {
        return webClient.get()
                .uri("/api/v1/user/my")
                .retrieve()
                .bodyToMono(UserClientRes.MyDto.class);
    }
}
