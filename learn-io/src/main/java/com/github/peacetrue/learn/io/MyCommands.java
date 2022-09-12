package com.github.peacetrue.learn.io;

import lombok.extern.slf4j.Slf4j;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

@Slf4j
@ShellComponent
public class MyCommands {

    @ShellMethod("写入文件内容")
    public void write(
            @ShellOption(value = {"-p", "--filePath"}, help = "文件路径", defaultValue = "bio.txt") String filePath,
            @ShellOption(value = {"-c", "--fileContent"}, help = "文件内容", defaultValue = "0") String fileContent,
            @ShellOption(value = {"-s", "--fileSize"}, help = "文件大小(M)", defaultValue = "1") int fileSize
    ) throws IOException {
        log.info("write '{}' into {} until {} M", fileContent, filePath, fileSize);
        write(Files.newOutputStream(Paths.get(filePath)), fileSize, fileContent);
    }

    @ShellMethod("写入文件内容(使用缓存区)")
    public void writeBuffer(
            @ShellOption(value = {"-p", "--filePath"}, help = "文件路径", defaultValue = "bio.txt") String filePath,
            @ShellOption(value = {"-c", "--fileContent"}, help = "文件内容", defaultValue = "0") String fileContent,
            @ShellOption(value = {"-s", "--fileSize"}, help = "文件大小(M)", defaultValue = "1") int fileSize
    ) throws IOException {
        log.info("write '{}' into {} until {} M", fileContent, filePath, fileSize);
        write(new BufferedOutputStream(Files.newOutputStream(Paths.get(filePath))), fileSize, fileContent);
    }

    private static void write(OutputStream outputStream, int fileSize, String fileContent) {
        fileSize *= 1024 * 1024;
        byte[] bytes = fileContent.getBytes(StandardCharsets.UTF_8);
        write(outputStream, fileSize, bytes);
    }

    private static void write(OutputStream outputStream, int fileSize, byte[] fileContent) {
        try (OutputStream localOutputStream = outputStream) {
            int max = fileSize / fileContent.length;
            for (int i = 0; i < max; i++) {
                localOutputStream.write(fileContent);
            }
            localOutputStream.flush();
        } catch (IOException e) {
            log.error("write error", e);
        }
    }

}
