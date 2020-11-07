package com.yahoo.ycsb.db;

import java.io.IOException;
import java.util.List;


import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.data.Stat;

// Implementing the PC Queue in ZooKeeper
//
public class ZKProducerConsumer implements Watcher {

    static ZooKeeper zk = null;
    static Integer mutex;

    String root;

    static public class QueueElement {
        public String key;
        public long writeTime;
    }

    // Producer-Consumer queue
    static public class Queue extends ZKProducerConsumer {
    }
}
        
