package com.github.peacetrue.learn.io;

import com.github.peacetrue.test.SourcePathUtils;
import org.apache.commons.io.IOUtils;
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

    /** 基本读写操作 */
    @Test
    void basic() throws IOException {
        // {projectDir}/src/test/resources/bio.txt
        // 文件可以不存在，写入时会自动创建；如果存在会覆盖内容
        File file = new File(SourcePathUtils.getTestResourceAbsolutePath("/bio.txt"));
        String content = RandomStringUtils.random(10); // 生成随机内容

        FileOutputStream fileOutputStream = new FileOutputStream(file);
        fileOutputStream.write(content.getBytes(StandardCharsets.UTF_8));
        // flush 仅作用于 buffered output bytes，此处是个反例
        fileOutputStream.flush();
        // fileOutputStream.getFD().sync();
        // fileOutputStream.getChannel().force(false);
        fileOutputStream.close();

        FileInputStream inputStream = new FileInputStream(file);
        String readContent = IOUtils.toString(inputStream);
        inputStream.close();
        Assertions.assertEquals(content, readContent);
    }
}
