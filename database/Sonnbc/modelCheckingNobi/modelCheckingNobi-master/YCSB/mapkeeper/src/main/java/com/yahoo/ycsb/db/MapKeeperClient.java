package com.yahoo.ycsb.db;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Properties;
import java.util.Set;
import java.util.Vector;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

import com.yahoo.mapkeeper.BinaryResponse;
import com.yahoo.mapkeeper.MapKeeper;
import com.yahoo.mapkeeper.Record;
import com.yahoo.mapkeeper.RecordListResponse;
import com.yahoo.mapkeeper.ResponseCode;
import com.yahoo.mapkeeper.ScanOrder;
import com.yahoo.ycsb.ByteIterator;
import com.yahoo.ycsb.DB;
import com.yahoo.ycsb.StringByteIterator;
import com.yahoo.ycsb.workloads.CoreWorkload;

public class MapKeeperClient extends DB {
    private static final String HOST = "mapkeeper.host";
    private static final String HOST_DEFAULT = "localhost";
    private static final String PORT = "mapkeeper.port";
    private static final String PORT_DEFAULT = "9090";
    MapKeeper.Client c; 
    boolean writeallfields;
    static boolean initteddb = false;
}
