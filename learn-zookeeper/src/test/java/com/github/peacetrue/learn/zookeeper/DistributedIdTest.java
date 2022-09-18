package com.github.peacetrue.learn.zookeeper;

import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.text.MessageFormat;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.LockSupport;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author : xiayx
 * @since : 2020-09-20 22:59
 **/
@Slf4j
public class DistributedIdTest {

    @Test
    void createId() throws Exception {
        ZooKeeper zooKeeper = ZooKeeperUtils.getZooKeeper();
        String nodePath = "/id";
        String path = zooKeeper.create(nodePath, null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        Assertions.assertEquals(nodePath, path);
    }

    @Test
    void generate() throws Exception {
//        int threads = 4_000;//QPS:[4050]
        int threads = 8_000;//QPS:[4306] QPS:[1757] with log
//        int threads = 12_000;//QPS:[3562]
//        int threads = 1;//QPS:[67]
        //QPS:[2,946]
        //QPS:[3,778]
        ZooKeeper zooKeeper = ZooKeeperUtils.getZooKeeper();
        Supplier<Long> longSupplier = () -> ZooKeeperUtils.generateId(zooKeeper);
        conn(longSupplier, threads);
        //        CompletableFuture<Long> completableFuture = ZooKeeperUtils
//                .completableFuture(consumer -> ZooKeeperUtils.generateId4callback(zooKeeper, consumer), executorService);

    }

    @Test
    void generate4e() throws Exception {
//        int threads = 4_000;//QPS:[4050]
        int threads = 8_000; //QPS:[5230] QPS:[4338] with log
//        int threads = 12_000;//QPS:[4718]
        ZooKeeper zooKeeper = ZooKeeperUtils.getZooKeeper();
        Set<Long> ids = Collections.synchronizedSet(new HashSet<>());
        long startTime = System.nanoTime();
        IntStream.range(0, threads).forEach(i -> ZooKeeperUtils.generateId4callback(zooKeeper, ids::add));
        System.out.println("over");
        while (true) if (ids.size() == threads) break;
        System.out.printf("QPS:[%s]", threads * 1_000_000_000L / (System.nanoTime() - startTime)).println();
    }

    @Test
    void generate4ev() throws Exception {
//        int threads = 4_000;//QPS:[4050]
//        int threads = 8_000; //QPS:[5311] QPS:[4832] with log
        int threads = 12_000;//QPS:[5957]
        ZooKeeper zooKeeper = ZooKeeperUtils.getZooKeeper();
        Set<Long> ids = Collections.synchronizedSet(new HashSet<>());
        long startTime = System.nanoTime();
        IntStream.range(0, threads).forEach(i -> ZooKeeperUtils.generateId4versionC(zooKeeper, ids::add));
        System.out.println("over");
        while (true) if (ids.size() == threads) break;
        System.out.printf("QPS:[%s]", threads * 1_000_000_000L / (System.nanoTime() - startTime)).println();
    }

    private void conn(Supplier<Long> longSupplier, int threads) {
        Thread thread = Thread.currentThread();
        ExecutorService executorService = Executors.newFixedThreadPool(4_000);
        List<Supplier<Long>> suppliers = IntStream.range(0, threads)
                .mapToObj(i -> longSupplier)
                .collect(Collectors.toList());
        long startTime = System.nanoTime();
        ZooKeeperUtils.supplyAsync(suppliers, executorService)
                .whenComplete((values, e) -> {
                    log.info("whenComplete {}", values, e);
                    if (e == null) {
                        long endTime = System.nanoTime();
                        log.info("QPS:[{}]", threads * 1_000_000_000L / (endTime - startTime));
                        System.out.printf("QPS:[%s]", threads * 1_000_000_000L / (endTime - startTime)).println();
                        Assertions.assertEquals(threads, new HashSet<>(values).size());
                        values.sort(Long::compareTo);
                        log.info("current:[{}]", values.get(values.size() - 1));
                        System.out.printf("current:[%s]", values.get(values.size() - 1)).println();
                    }
                    LockSupport.unpark(thread);
                });
        LockSupport.park();
    }

    @Test
    void generate4v() throws Exception {
        Thread thread = Thread.currentThread();
        int threads = 8_000;//QPS:[4,497]
//        int threads = 1;//QPS:[2,333]
        ZooKeeper zooKeeper = ZooKeeperUtils.getZooKeeper();
        Supplier<Long> longSupplier = () -> ZooKeeperUtils.generateId4version(zooKeeper);
        //QPS:[2,946]
        //QPS:[3,778]
        ExecutorService executorService = Executors.newFixedThreadPool(4_000);
        List<Supplier<Long>> suppliers = IntStream.range(0, threads)
                .mapToObj(i -> longSupplier)
                .collect(Collectors.toList());
        long startTime = System.nanoTime();
        ZooKeeperUtils.supplyAsync(suppliers, executorService)
                .whenComplete((values, e) -> {
                    log.info("whenComplete {}", values, e);
                    if (e == null) {
                        long endTime = System.nanoTime();
                        log.info("QPS:[{}]", threads * 1_000_000_000L / (endTime - startTime));
                        System.out.println(MessageFormat.format("QPS:[{0}]", threads * 1_000_000_000L / (endTime - startTime)));
                        Assertions.assertEquals(threads, new HashSet<>(values).size());
                        values.sort(Long::compareTo);
                        log.info("current:[{}]", values.get(values.size() - 1));
                    }
                    LockSupport.unpark(thread);
                });
        LockSupport.park();
    }


}
