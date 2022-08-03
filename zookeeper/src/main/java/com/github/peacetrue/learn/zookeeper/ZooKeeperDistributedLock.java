package com.github.peacetrue.learn.zookeeper;

import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author : xiayx
 * @since : 2020-09-26 05:37
 **/
@Slf4j
public class ZooKeeperDistributedLock implements Lock {

    private ZooKeeper zooKeeper;
    private String lockPath;
    private String lockPath_;
    private ReentrantLock localLock = new ReentrantLock();
    private static final ThreadLocal<String> threadLocal = new ThreadLocal<>();

    public ZooKeeperDistributedLock(ZooKeeper zooKeeper, String lockPath) {
        this.zooKeeper = zooKeeper;
        this.lockPath = lockPath;
        this.lockPath_ = lockPath + "/";
    }

    @Override
    public void lock() {
        log.info("lock");
        try {
            localLock.lock();
            String sequentialPath = zooKeeper.create(lockPath_, null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
            log.info("create path: {}", sequentialPath);
            threadLocal.set(sequentialPath);
        } catch (KeeperException | InterruptedException e) {
            localLock.unlock();
            throw new IllegalStateException(e);
        }
        this.lock(threadLocal.get().substring(lockPath_.length()), true, new CountDownLatch(1));
    }

    public void lock(String actualPath, boolean first, CountDownLatch countDownLatch) {
        try {
            List<String> children = zooKeeper.getChildren(lockPath, false);
            children.sort(String::compareTo);
            log.info("get [{}]' children {}", lockPath, children);
            if (actualPath.equals(children.get(0))) {
                if (!first) countDownLatch.countDown();
                log.info("{} get lock", actualPath);
                return;
            }

            int index = children.indexOf(actualPath);
            String path = lockPath_ + children.get(index - 1);
            log.info("get [{}] pre node path:[{}]", actualPath, path);
            Stat stat = zooKeeper.exists(path, event -> {
                if (Watcher.Event.EventType.NodeDeleted == event.getType()) {
                    log.info("pre node[{}] deleted current node[{}] get lock", event.getPath(), actualPath);
                    //如果是主动删除，直接获得锁
                    //countDownLatch.countDown();
                    //如果是断线，不能获得锁
                    this.lock(actualPath, false, countDownLatch);
                }
            });
            //查询的时候有，监听的时候消失了，重新监听
            if (stat == null) {
                log.info("node [{}] not exists", path);
                this.lock(actualPath, first, countDownLatch);
                return;
            }
            log.info("node [{}] wait lock", actualPath);
            countDownLatch.await();
            log.info("node [{}] run from wait", actualPath);
        } catch (KeeperException | InterruptedException e) {
            System.err.println(e);
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock() {
        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public void unlock() {
        try {
            String sequentialPath = threadLocal.get();
            log.info("unlock node [{}]", sequentialPath);
            zooKeeper.delete(sequentialPath, -1);
            localLock.unlock();
            threadLocal.remove();
        } catch (InterruptedException | KeeperException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public Condition newCondition() {
        return null;
    }
}
