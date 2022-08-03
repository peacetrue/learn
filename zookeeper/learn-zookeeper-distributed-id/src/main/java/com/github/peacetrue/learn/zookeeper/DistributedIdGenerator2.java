package com.github.peacetrue.learn.zookeeper;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooKeeper;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DistributedIdGenerator2 {

    private ZooKeeper zooKeeper;
    private String type;

    public Long generate() {
        try {
            String path = zooKeeper.create(type, null, null, CreateMode.PERSISTENT_SEQUENTIAL);
        } catch (Exception e) {
            throw new IllegalStateException("获取 id 失败", e);
        }
        throw new IllegalStateException("获取 id 失败");
    }

}
