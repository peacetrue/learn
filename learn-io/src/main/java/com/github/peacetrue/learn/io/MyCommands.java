package com.github.peacetrue.learn.io;

import lombok.extern.slf4j.Slf4j;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
@ShellComponent
public class MyCommands {

    @ShellMethod("写入文件内容")
    public void write(
            @ShellOption(value = {"-p", "--filePath"}, help = "文件路径", defaultValue = "bio.txt") String filePath,
            @ShellOption(value = {"-c", "--fileContent"}, help = "文件内容", defaultValue = "0") String fileContent,
            @ShellOption(value = {"-s", "--fileSize"}, help = "文件大小(M)", defaultValue = "5") int fileSize
    ) {
        log.info("write '{}' into {} until {} M", fileContent, filePath, fileSize);
        fileSize *= 1024 * 1024;
        byte[] bytes = fileContent.getBytes(StandardCharsets.UTF_8);
        try (FileOutputStream inputStream = new FileOutputStream(filePath)) {
            int max = fileSize / bytes.length;
            for (int i = 0; i < max; i++) {
                inputStream.write(bytes);
            }
            inputStream.flush();
        } catch (IOException e) {
            log.error("write error", e);
        }
    }

}
