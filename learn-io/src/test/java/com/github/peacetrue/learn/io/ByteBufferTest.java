package com.github.peacetrue.learn.io;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.nio.ByteBuffer;

/**
 * @author peace
 **/
@Slf4j
public class ByteBufferTest {

    @Test
    void basic() {
        ByteBuffer buffer = ByteBuffer.allocate(10);
        log.info("buffer: {}", buffer);
        buffer.putInt('a');
        log.info("buffer: {}", buffer);
        buffer.putInt('a');
        log.info("buffer: {}", buffer);
        buffer.flip();
        log.info("buffer: {}", buffer.getInt());
        log.info("buffer: {}", buffer);
        buffer.compact();
        log.info("buffer: {}", buffer);
        buffer.clear();
        log.info("buffer: {}", buffer);
    }
}
