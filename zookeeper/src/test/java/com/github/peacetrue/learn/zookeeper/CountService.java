package com.github.peacetrue.learn.zookeeper;

/**
 * @author : xiayx
 * @since : 2020-09-26 07:54
 **/
public interface CountService {

    default void reset() {
    }


    Long get();

    Long getAndIncr();
}
