package com.github.peacetrue.learn.strace;

/**
 * @author peace
 **/
public class SleepTest {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("------");
        Thread.sleep(10_000);
        System.out.println("------");
    }
}
