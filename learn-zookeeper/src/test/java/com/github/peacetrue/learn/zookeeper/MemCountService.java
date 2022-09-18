package com.github.peacetrue.learn.zookeeper;

/**
 * @author : xiayx
 * @since : 2020-09-26 07:54
 **/
public class MemCountService implements CountService {

    private Long count = 0L;

    @Override
    public Long get() {
        return count;
    }

    public Long getAndIncr() {
        return ++count;
    }
}
