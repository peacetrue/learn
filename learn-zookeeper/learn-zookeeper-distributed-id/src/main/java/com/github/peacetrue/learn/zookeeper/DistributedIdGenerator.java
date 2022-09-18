package com.github.peacetrue.learn.zookeeper;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.atomic.AtomicValue;
import org.apache.curator.framework.recipes.atomic.DistributedAtomicLong;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DistributedIdGenerator {

    private DistributedAtomicLong distributedAtomicLong;
    private String type;

    public DistributedIdGenerator(String type, CuratorFramework curator, RetryPolicy retryPolicy) {
        this.type = type;
        this.distributedAtomicLong = new DistributedAtomicLong(curator, type, retryPolicy);
    }

    public Long generate() {
        try {
            AtomicValue<Long> value = distributedAtomicLong.increment();
            if (value.succeeded()) return value.postValue();
        } catch (Exception e) {
            throw new IllegalStateException("获取 id 失败", e);
        }
        throw new IllegalStateException("获取 id 失败");
    }

}
