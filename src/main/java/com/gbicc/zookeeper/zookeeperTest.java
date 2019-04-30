package com.gbicc.zookeeper;

import org.apache.zookeeper.ZooKeeper;

import java.util.List;

public class zookeeperTest {
    public static void main(String[] args) throws Exception {
        ZooKeeper zk = new ZooKeeper("192.168.1.61:2181", 10000, null);
        List<String> ids = zk.getChildren("/brokers/ids", false);
        for (String id : ids) {
            String brokerInfo = new String(zk.getData("/brokers/ids/" + id, false, null));
            System.out.println(id + ": " + brokerInfo);
        }
    }
}
