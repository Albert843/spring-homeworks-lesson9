package ru.gb.issue_service;

import com.netflix.discovery.EurekaClient;
import org.springframework.cloud.client.loadbalancer.reactive.ReactorLoadBalancerExchangeFilterFunction;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.UUID;

@Component
public class ReaderProvider {

    private final WebClient webClient;

    public ReaderProvider(EurekaClient eurekaClient, ReactorLoadBalancerExchangeFilterFunction loadBalancerExchangeFilterFunction) {
        webClient = WebClient.builder()
                .filter(loadBalancerExchangeFilterFunction)
                .build();
    }

    public UUID getRandomBookId() {
        Reader randomReader = webClient.get()
                .uri("http://reader-service/reader/random")
                .retrieve()
                .bodyToMono(Reader.class)
                .block();

        return randomReader.getId();
    }
}
