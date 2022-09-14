package com.github.peacetrue.learn.io;

import com.github.peacetrue.time.ElapsedTimeUtils;
import com.github.peacetrue.util.concurrent.CompletableFutureUtils;
import lombok.extern.slf4j.Slf4j;
import org.jooq.lambda.Unchecked;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import javax.annotation.processing.Completions;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.CompletableFuture;

@Slf4j
@ShellComponent
public class IOCommands {

    @ShellMethod("写入文件内容")
    public void write(
            @ShellOption(value = {"-p", "--filePath"}, help = "文件路径", defaultValue = ".") String filePath,
            @ShellOption(value = {"-s", "--fileSize"}, help = "文件容量(M)", defaultValue = "1") int fileSize,
            @ShellOption(value = {"-c", "--fileContent"}, help = "文件内容", defaultValue = "0") String fileContent
    ) {
        CompletableFutureUtils.allOf(
                        CompletableFuture.supplyAsync(() -> ElapsedTimeUtils.evaluate(Unchecked.runnable(() -> writeDirectly(filePath + "/directly.txt", fileSize, fileContent)))),
                        CompletableFuture.supplyAsync(() -> ElapsedTimeUtils.evaluate(Unchecked.runnable(() -> writeBuffer(filePath + "/buffered.txt", fileSize, fileContent))))
                )
                .thenAccept(values -> {
                    log.info("write directly elapse {} ", Duration.of(values.get(0), ChronoUnit.MILLIS));
                    log.info("write buffered elapse {} ", Duration.of(values.get(1), ChronoUnit.MILLIS));
                });
    }

    private static void writeDirectly(String filePath, int fileSize, String fileContent) throws IOException {
        log.info("write '{}' into {} directly until {} M", fileContent, filePath, fileSize);
        write(Files.newOutputStream(Paths.get(filePath)), fileSize, fileContent);
    }

    private static void writeBuffer(String filePath, int fileSize, String fileContent) throws IOException {
        log.info("write '{}' into {} buffered until {} M", fileContent, filePath, fileSize);
        write(new BufferedOutputStream(Files.newOutputStream(Paths.get(filePath)), 8192), fileSize, fileContent);
    }

    //tag::write[]

    /**
     * 写出文件内容直至指定容量。
     *
     * @param outputStream 输出流
     * @param fileSize     文件容量（M）
     * @param fileContent  文件内容
     */
    private static void write(OutputStream outputStream, int fileSize, String fileContent) {
        fileSize *= 1024 * 1024;
        byte[] fileContentBytes = fileContent.getBytes(StandardCharsets.UTF_8);
        try (OutputStream localOutputStream = outputStream) {
            int max = fileSize / fileContentBytes.length;
            for (int i = 0; i < max; i++) {
                localOutputStream.write(fileContentBytes);
            }
            localOutputStream.flush();
        } catch (IOException e) {
            log.error("write error", e);
        }
    }
    //end::write[]

}
