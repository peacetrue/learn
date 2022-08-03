package com.github.peacetrue.learn.zookeeper;

import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.ZooKeeper;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.LockSupport;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author : xiayx
 * @since : 2020-09-26 06:08
 **/
@Slf4j
class ZooKeeperDistributedLockTest {

    private void concurrent(CountService countService, Runnable runnable) {
        ExecutorService executorService = Executors.newFixedThreadPool(100);
        int count = 100;
        List<Runnable> runnables = IntStream.range(0, count).mapToObj(i -> runnable).collect(Collectors.toList());
        Thread thread = Thread.currentThread();
        ZooKeeperUtils.runAsync(runnables, executorService)
                .whenComplete((a, b) -> {
                    log.info("count: {}", countService.get());
                    countService.reset();
                    log.info("count after reset: {}", countService.get());
                    LockSupport.unpark(thread);
                });
        LockSupport.park(this);
    }

    @Test
    void noLock() {
        MemCountService memLocalService = new MemCountService();
        Runnable runnable = memLocalService::getAndIncr;
        concurrent(memLocalService, runnable);
    }

    @Test
    void noLockTxt() {
        LocalCountService memLocalService = new LocalCountService();
        Runnable runnable = memLocalService::getAndIncr;
        concurrent(memLocalService, runnable);
    }

    private CompletableFuture<Void> lock() throws Exception {
        ZooKeeper zooKeeper = ZooKeeperUtils.getZooKeeper();
        CountService countService = new LocalCountService();
        ZooKeeperDistributedLock lock = new ZooKeeperDistributedLock(zooKeeper, "/lock");
        Runnable runnable = () -> {
            try {
                lock.lock();
                countService.getAndIncr();
            } catch (Exception e) {
                throw new IllegalStateException(e);
            } finally {
                lock.unlock();
            }
        };
        ExecutorService executorService = Executors.newFixedThreadPool(100);
        int count = 200;
        List<Runnable> runnables = IntStream.range(0, count).mapToObj(i -> runnable).collect(Collectors.toList());
        return ZooKeeperUtils.runAsync(runnables, executorService);
    }

    @Test
    void localLock() throws Exception {
        Thread thread = Thread.currentThread();
        lock()
                .whenComplete((aVoid, throwable) -> {
                    if (throwable != null) {
                        log.error("error", throwable);
                        LockSupport.unpark(thread);
                        return;
                    }
                    CountService countService = new LocalCountService();
                    log.info("----- finished count: {}", countService.get());
                    countService.reset();
                    log.info("----- finished count after reset: {}", countService.get());
                    LockSupport.unpark(thread);
                });
        log.info("LockSupport.park");
        LockSupport.park(this);
    }

    @Test
    void distributedLock() throws Exception {
        long startTime = System.currentTimeMillis();
        Thread thread = Thread.currentThread();
        CompletableFuture.allOf(lock(), lock(), lock(), lock())
                .whenComplete((aVoid, throwable) -> {
                    //3858
                    //4538
                    log.info("TOTAL TIME:[{}]", System.currentTimeMillis() - startTime);
                    if (throwable != null) {
                        log.error("error", throwable);
                        LockSupport.unpark(thread);
                        return;
                    }
                    CountService countService = new LocalCountService();
                    log.info("----- finished count: {}", countService.get());
                    countService.reset();
                    log.info("----- finished count after reset: {}", countService.get());
                    LockSupport.unpark(thread);
                });
        log.info("LockSupport.park");
        LockSupport.park(this);
    }

}
