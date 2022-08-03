package com.github.peacetrue.learn.zookeeper;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@SpringBootApplication
public class ResourceServerApplication {

    @RequestMapping("/")
    public Mono<String> home() {
        return Mono.just("Hello world");
    }

    public static void main(String[] args) {
        new SpringApplicationBuilder(ResourceServerApplication.class).run(args);
    }

}
