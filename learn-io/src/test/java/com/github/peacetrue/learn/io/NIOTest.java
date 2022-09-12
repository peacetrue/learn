package com.github.peacetrue.learn.io;

import com.github.peacetrue.test.SourcePathUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author peace
 **/
@Slf4j
public class NIOTest {

    @Test
    void basic() throws IOException {
        String string = RandomStringUtils.random(10);
        Path filePath = Paths.get(SourcePathUtils.getTestResourceAbsolutePath("/bio.txt"));

        OutputStream outputStream = Files.newOutputStream(filePath);
        log.info("outputStream: {}", outputStream);
        outputStream.write(string.getBytes(StandardCharsets.UTF_8));
        outputStream.flush();
        outputStream.close();

        InputStream inputStream = Files.newInputStream(filePath);
        log.info("inputStream: {}", inputStream);
        Assertions.assertEquals(string, IOUtils.toString(inputStream));
        inputStream.close();
    }

    @Test
    void channel() throws IOException {
        String string = RandomStringUtils.random(10);
        ByteBuffer buffer = ByteBuffer.wrap(string.getBytes(StandardCharsets.UTF_8));
        log.info("buffer: {}", buffer);

        String path = SourcePathUtils.getTestResourceAbsolutePath("/nio.txt");
        RandomAccessFile file = new RandomAccessFile(path, "rw");
        FileChannel channel = file.getChannel();
        channel.write(buffer);
        channel.force(true);
        channel.close();
    }
}
