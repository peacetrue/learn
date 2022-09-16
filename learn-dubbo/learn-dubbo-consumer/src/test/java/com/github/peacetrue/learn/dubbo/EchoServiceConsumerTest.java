package com.github.peacetrue.learn.dubbo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author peace
 **/
@SpringBootTest
class EchoServiceConsumerTest {

    @Autowired
    private EchoController echoController;

    @Test
    void echo() {
        String input = "test";
        Assertions.assertEquals(input, echoController.echo(input));
    }
}
