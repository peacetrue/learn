package com.github.peacetrue.learn.io;

import com.github.peacetrue.test.SourcePathUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @author peace
 **/
public class BIOTest {

    @Test
    void name() throws IOException {
        System.in.read();
    }

    /** 读写内容 */
    @Test
    void basic() throws IOException {
        File file = new File(SourcePathUtils.getTestResourceAbsolutePath("/nio.txt"));
        String content = RandomStringUtils.random(10);

        FileOutputStream fileOutputStream = new FileOutputStream(file);
        fileOutputStream.write(content.getBytes(StandardCharsets.UTF_8));
        fileOutputStream.flush();
        fileOutputStream.getFD().sync();
        fileOutputStream.getChannel().force(false);
        fileOutputStream.close();

        FileInputStream inputStream = new FileInputStream(file);
        Assertions.assertEquals(content, new String(inputStream.readAllBytes(), StandardCharsets.UTF_8));
        inputStream.close();
    }
}
