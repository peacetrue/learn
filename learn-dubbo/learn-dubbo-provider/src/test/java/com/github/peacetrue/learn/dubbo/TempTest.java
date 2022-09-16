package com.github.peacetrue.learn.dubbo;

import org.junit.jupiter.api.Test;

/**
 * @author peace
 **/
public class TempTest {

    @Test
    void name() {
        System.out.println(System.getenv().get("LEARN_DUBBO_DOMAIN"));
        System.out.println(System.getenv().get("LEARN_DUBBO_CONSUMER_PORT"));
    }
}
