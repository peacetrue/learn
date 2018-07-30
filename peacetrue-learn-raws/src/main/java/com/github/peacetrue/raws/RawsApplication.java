package com.github.peacetrue.raws;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @author xiayx
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class RawsApplication {

    public static void main(String[] args) {
        SpringApplication.run(RawsApplication.class, args);
    }
}
