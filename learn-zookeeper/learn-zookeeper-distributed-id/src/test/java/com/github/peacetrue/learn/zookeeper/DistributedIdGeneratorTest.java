package com.github.peacetrue.learn.zookeeper;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.LockSupport;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author : xiayx
 * @since : 2020-09-20 15:43
 **/
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
class DistributedIdGeneratorTest {

    @Autowired
    private DistributedIdGenerator distributedIdGenerator;

    /** @see CompletableFuture#supplyAsync(Supplier, Executor) */
    public static <T> CompletableFuture<List<T>> supplyAsync(List<Supplier<T>> suppliers, Executor executor) {
        List<CompletableFuture<T>> futures = suppliers.stream()
                .map(supplier -> CompletableFuture.supplyAsync(supplier, executor))
                .collect(Collectors.toList());
        return CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]))
                .thenApplyAsync((voids) -> futures.stream().map(CompletableFuture::join).collect(Collectors.toList()), executor);

    }

    @Test
    void generate() {
        Thread thread = Thread.currentThread();
        int threads = 1;
        ExecutorService executorService = Executors.newFixedThreadPool(50);
        List<Supplier<Long>> suppliers = IntStream.range(0, threads)
                .mapToObj(i -> (Supplier<Long>) distributedIdGenerator::generate)
                .collect(Collectors.toList());
        long startTime = System.nanoTime();
        supplyAsync(suppliers, executorService)
                .whenComplete((values, e) -> {
                    log.info("[values={}]", values, e);
                    LockSupport.unpark(thread);

                    if (e != null) {
                        Assertions.fail(e);
                    } else {
                        long endTime = System.nanoTime();
                        log.info("qps:[{}]", threads * 1_000_000_000L / (endTime - startTime));
                        Assertions.assertEquals(threads, new HashSet<>(values).size());
                        values.sort(Long::compareTo);
                        log.info("current:[{}]", values.get(values.size() - 1));
                    }
                });
        LockSupport.park();
    }
}
//netstat -an |grep 'ESTABLISHED' |grep -i '2181' |wc -l
