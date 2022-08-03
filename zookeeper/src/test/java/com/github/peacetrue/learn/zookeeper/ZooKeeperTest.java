package com.github.peacetrue.learn.zookeeper;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author : xiayx
 * @since : 2020-09-20 00:08
 **/
@Slf4j
class ZooKeeperTest {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    /** 连接到 zookeeper */
    @Test
    void connect() throws Exception {
        ZooKeeper zooKeeper = ZooKeeperUtils.getZooKeeper();
        log.info("zookeeper: [{}]", zooKeeper);
    }

    /** 在连接的节点故障后，集群仍然可用时，重新连接到其他节点，并接受事件通知 */
    @Test
    void reconnect() throws Exception {
        ZooKeeper zooKeeper = ZooKeeperUtils.getZooKeeper();
        log.info("zookeeper: [{}]", zooKeeper);
        Thread.sleep(1_000_000);
        zooKeeper.close();
    }

    /** 在连接的节点故障后，集群不可用时的表现 */
    @Test
    void reconnectAfterUnavailable() throws Exception {
        ZooKeeper zooKeeper = ZooKeeperUtils.getZooKeeper();
        log.info("zookeeper: [{}]", zooKeeper);
        System.out.println();
        Thread.sleep(1_000_000);
        zooKeeper.close(10_000);
    }

    /** 基本操作阻塞模型 */
    @Test
    void basicOperateBlock() throws Exception {
        ZooKeeper zooKeeper = ZooKeeperUtils.getZooKeeper();
        System.out.println();

        String nodePath = "/test";
        String nodeData = "hello";
        Stat nodeStat = new Stat();
        String actualNodePath = zooKeeper.create(nodePath, nodeData.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL, nodeStat, -1);
        log.info("create node[{}-{}] return node[{}-{}]", nodePath, nodeData, actualNodePath, objectMapper.writeValueAsString(nodeStat));

        Stat existsStat = zooKeeper.exists(actualNodePath, true);
        log.info("exists node[{}-{}]", actualNodePath, objectMapper.writeValueAsString(existsStat));
        Assertions.assertEquals(nodeStat, existsStat);

        byte[] getNodeDataBytes = zooKeeper.getData(actualNodePath, true, existsStat);
        String getNodeData = new String(getNodeDataBytes);
        log.info("get node[{}] data: [{}]", actualNodePath, getNodeData);
        Assertions.assertEquals(nodeData, getNodeData);

        String setDataNodeData = "hello-changed";
        Stat setDataNodeStat = zooKeeper.setData(actualNodePath, setDataNodeData.getBytes(), existsStat.getVersion());
        log.info("set node[{}] data[{}] return [{}]", actualNodePath, setDataNodeData, objectMapper.writeValueAsString(setDataNodeStat));
        Assertions.assertNotEquals(nodeStat, setDataNodeStat);

        try {
            setDataNodeStat = zooKeeper.setData(actualNodePath, setDataNodeData.getBytes(), existsStat.getVersion());
            Assertions.fail("必须抛出异常");
        } catch (KeeperException | InterruptedException e) {
            log.info("指定了错误版本号设置数据:[{}]", e.getMessage());
        }

        //上面错误的修改操作不会导致版本号变更
        zooKeeper.delete(actualNodePath, setDataNodeStat.getVersion());
    }

    @Test
    void createExistNode() throws Exception {
        ZooKeeper zooKeeper = ZooKeeperUtils.getZooKeeper();
        System.out.println();

        String nodePath = "/test";
        String nodeData = "hello";
        Stat nodeStat = new Stat();
        String actualNodePath = zooKeeper.create(nodePath, nodeData.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT, nodeStat, -1);
        log.info("create node[{}-{}] return node[{}-{}]", nodePath, nodeData, actualNodePath, objectMapper.writeValueAsString(nodeStat));

        //KeeperErrorCode = NodeExists for /test
        actualNodePath = zooKeeper.create(nodePath, nodeData.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT, nodeStat, -1);
        log.info("create node[{}-{}] return node[{}-{}]", nodePath, nodeData, actualNodePath, objectMapper.writeValueAsString(nodeStat));
    }

    /** 基本操作非阻塞模型 */
    @Test
    void basicOperateNonblock() throws Exception {
        String nodePath = "/test";
        String nodeData = "hello";
        ZooKeeperUtils.getZooKeeperFuture()
                .whenComplete((zooKeeper, throwable) -> {
                    zooKeeper.create(nodePath, nodeData.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL, (rc, path, ctx, name, stat) -> {

                    }, null, -1);
                });
    }

}
