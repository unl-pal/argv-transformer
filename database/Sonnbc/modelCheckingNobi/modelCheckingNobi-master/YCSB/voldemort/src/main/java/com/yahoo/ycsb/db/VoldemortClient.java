package com.yahoo.ycsb.db;

import java.util.HashMap;
import java.util.Set;
import java.util.Vector;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import voldemort.client.ClientConfig;
import voldemort.client.SocketStoreClientFactory;
import voldemort.client.StoreClient;
import voldemort.versioning.VectorClock;
import voldemort.versioning.Versioned;

import com.yahoo.ycsb.DB;
import com.yahoo.ycsb.DBException;
import com.yahoo.ycsb.ByteIterator;
import com.yahoo.ycsb.StringByteIterator;


public class VoldemortClient extends DB {

	private StoreClient<String, HashMap<String, String>> storeClient;
	private SocketStoreClientFactory socketFactory;
	private String storeName;
    private final Logger logger = Logger.getLogger(VoldemortClient.class);
    
    public static final int OK = 0;
    public static final int ERROR = -1;
    public static final int NOT_FOUND = -2;

}
