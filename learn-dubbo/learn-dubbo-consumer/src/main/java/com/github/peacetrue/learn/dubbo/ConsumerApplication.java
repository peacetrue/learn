package com.github.peacetrue.learn.dubbo;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableDubbo
@SpringBootApplication
public class ConsumerApplication {

    public static void main(String[] args) {
        System.setProperty("dubbo.meta.cache.filePath", "./.dubbo/learn-dubbo-consumer");
        System.setProperty("dubbo.mapping.cache.filePath", "./.dubbo/learn-dubbo-consumer");
        SpringApplication.run(ConsumerApplication.class, args);
    }

}
