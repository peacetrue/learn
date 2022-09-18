package com.github.peacetrue.learn.zookeeper;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author : xiayx
 * @since : 2020-09-26 07:54
 **/
@Slf4j
public class LocalCountService implements CountService {

    private Path path = Paths.get("/Users/xiayx/Documents/Projects/learn-zookeeper/src/test/resources/count.txt");

    public void reset() {
        try {
            log.info("reset count to 0");
            Files.write(path, "0".getBytes());
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public Long get() {
        try {
            return Long.parseLong(new String(Files.readAllBytes(path)).replace("\n", ""));
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    public Long getAndIncr() {
        long count = get() + 1;
        try {
            log.info("incr from {} to {}", count - 1, count);
            Files.write(path, String.valueOf(count).getBytes());
            return count;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }
}
