package com.petdiary.clients.petdiaryauthclient.service;

import com.petdiary.clients.petdiaryauthclient.dto.UserClientRes;
import com.petdiary.clients.petdiaryauthclient.properties.AuthClientProperties;
import com.petdiary.core.dto.ComResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class UserClientSvc {
    private final WebClient webClient;

    @Autowired
    public UserClientSvc(WebClient.Builder webClientBuilder, AuthClientProperties authClientProperties) {
        this.webClient = webClientBuilder
                .baseUrl(authClientProperties.getBaseUrl())
                .build();
    }

    public Mono<UserClientRes.MyDto> getUserData(String token) {
        return webClient.get()
                .uri("/api/v1/user/my")
                .header("Authorization", token)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ComResponseDto<UserClientRes.MyDto>>() {})
                .map(ComResponseDto::getBody);
    }
}
