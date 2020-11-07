package com.yahoo.ycsb.db;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;
import java.util.Vector;
import java.util.concurrent.TimeUnit;

import org.apache.accumulo.core.client.AccumuloException;
import org.apache.accumulo.core.client.AccumuloSecurityException;
import org.apache.accumulo.core.client.BatchWriter;
import org.apache.accumulo.core.client.BatchWriterConfig;
import org.apache.accumulo.core.client.Connector;
import org.apache.accumulo.core.client.MutationsRejectedException;
import org.apache.accumulo.core.client.Scanner;
import org.apache.accumulo.core.client.TableNotFoundException;
import org.apache.accumulo.core.client.ZooKeeperInstance;
import org.apache.accumulo.core.client.security.tokens.AuthenticationToken;
import org.apache.accumulo.core.client.security.tokens.PasswordToken;
import org.apache.accumulo.core.data.Key;
import org.apache.accumulo.core.data.Mutation;
import org.apache.accumulo.core.data.Range;
import org.apache.accumulo.core.data.Value;
import org.apache.accumulo.core.security.Authorizations;
import org.apache.accumulo.core.util.CleanUp;
import org.apache.hadoop.io.Text;
import org.apache.zookeeper.KeeperException;

import com.yahoo.ycsb.ByteArrayByteIterator;
import com.yahoo.ycsb.ByteIterator;
import com.yahoo.ycsb.DB;
import com.yahoo.ycsb.DBException;

public class AccumuloClient extends DB {
	// Error code constants.
	public static final int Ok = 0;
	public static final int ServerError = -1;
	public static final int HttpError = -2;
	public static final int NoMatchingRecord = -3;

	private ZooKeeperInstance _inst;
	private Connector _connector;
	private String _table = "";
	private BatchWriter _bw = null;
	private Text _colFam = new Text("");
	private Scanner _singleScanner = null;    // A scanner for reads/deletes.
	private Scanner _scanScanner = null;      // A scanner for use by scan()

	private static final String PC_PRODUCER = "producer";
	private static final String PC_CONSUMER = "consumer";
	private String _PC_FLAG = "";
	private ZKProducerConsumer.Queue q = null;
	private static Hashtable<String,Long> hmKeyReads = null;
	private static Hashtable<String,Integer> hmKeyNumReads = null;
	private Random r = null;

}
