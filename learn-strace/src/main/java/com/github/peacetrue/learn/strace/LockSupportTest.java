package com.github.peacetrue.learn.strace;

import java.util.concurrent.locks.LockSupport;

/**
 * @author peace
 **/
public class LockSupportTest {
    public static void main(String[] args) {
        System.out.println("------");
        LockSupport.unpark(Thread.currentThread());
        System.out.println("------");
        LockSupport.parkNanos(1_000);
        System.out.println("------");
    }
}
