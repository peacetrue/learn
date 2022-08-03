package com.github.peacetrue.learn.zookeeper;


import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DistributedIdApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(DistributedIdApplication.class).run(args);
    }

    @Bean
    public DistributedIdGenerator distributedIdGenerator(CuratorFramework curatorFramework, RetryPolicy retryPolicy) {
        return new DistributedIdGenerator("/order", curatorFramework, retryPolicy);
    }
}
