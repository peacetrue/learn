package com.github.peacetrue.learn.zookeeper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.loadbalancer.reactive.ReactorLoadBalancerExchangeFilterFunction;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
@SpringBootApplication
public class ResourceClientApplication {

    @Autowired
    private ReactorLoadBalancerExchangeFilterFunction lbFunction;

    @RequestMapping("/")
    public Mono<String> home() {
        return WebClient.builder()
                .filter(lbFunction)
                .build().get().uri("http://resource-server")
                .retrieve().bodyToMono(String.class);
    }

    public static void main(String[] args) {
        new SpringApplicationBuilder(ResourceClientApplication.class).run(args);
        // http://localhost:7001/
    }

}
