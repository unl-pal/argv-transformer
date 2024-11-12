package com.yahoo.ycsb.db;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.Vector;

import com.gemstone.gemfire.cache.Cache;
import com.gemstone.gemfire.cache.CacheFactory;
import com.gemstone.gemfire.cache.GemFireCache;
import com.gemstone.gemfire.cache.Region;
import com.gemstone.gemfire.cache.RegionExistsException;
import com.gemstone.gemfire.cache.RegionFactory;
import com.gemstone.gemfire.cache.RegionShortcut;
import com.gemstone.gemfire.cache.client.ClientCache;
import com.gemstone.gemfire.cache.client.ClientCacheFactory;
import com.gemstone.gemfire.cache.client.ClientRegionFactory;
import com.gemstone.gemfire.cache.client.ClientRegionShortcut;
import com.gemstone.gemfire.internal.admin.remote.DistributionLocatorId;
import com.yahoo.ycsb.ByteArrayByteIterator;
import com.yahoo.ycsb.ByteIterator;
import com.yahoo.ycsb.DB;
import com.yahoo.ycsb.DBException;
import com.yahoo.ycsb.StringByteIterator;

/**
 * VMware vFabric GemFire client for the YCSB benchmark.<br />
 * <p>By default acts as a GemFire client and tries to connect
 * to GemFire cache server running on localhost with default
 * cache server port. Hostname and port of a GemFire cacheServer
 * can be provided using <code>gemfire.serverport=port</code> and <code>
 * gemfire.serverhost=host</code> properties on YCSB command line.
 * A locator may also be used for discovering a cacheServer
 * by using the property <code>gemfire.locator=host[port]</code></p>
 * 
 * <p>To run this client in a peer-to-peer topology with other GemFire
 * nodes, use the property <code>gemfire.topology=p2p</code>. Running
 * in p2p mode will enable embedded caching in this client.</p>
 * 
 * <p>YCSB by default does its operations against "usertable". When running
 * as a client this is a <code>ClientRegionShortcut.PROXY</code> region,
 * when running in p2p mode it is a <code>RegionShortcut.PARTITION</code>
 * region. A cache.xml defining "usertable" region can be placed in the
 * working directory to override these region definitions.</p>
 * 
 * @author Swapnil Bawaskar (sbawaska at vmware)
 *
 */
public class GemFireClient extends DB {

  /** Return code when operation succeeded */
  private static final int SUCCESS = 0;

  /** Return code when operation did not succeed */
  private static final int ERROR = -1;

  /** property name of the port where GemFire server is listening for connections */
  private static final String SERVERPORT_PROPERTY_NAME = "gemfire.serverport";

  /** property name of the host where GemFire server is running */
  private static final String SERVERHOST_PROPERTY_NAME = "gemfire.serverhost";

  /** default value of {@link #SERVERHOST_PROPERTY_NAME} */
  private static final String SERVERHOST_PROPERTY_DEFAULT = "localhost";

  /** property name to specify a GemFire locator. This property can be used in both
   * client server and p2p topology */
  private static final String LOCATOR_PROPERTY_NAME = "gemfire.locator";

  /** property name to specify GemFire topology */
  private static final String TOPOLOGY_PROPERTY_NAME = "gemfire.topology";

  /** value of {@value #TOPOLOGY_PROPERTY_NAME} when peer to peer topology should be used.
   *  (client-server topology is default) */
  private static final String TOPOLOGY_P2P_VALUE = "p2p";

  private GemFireCache cache;

  /**
   * true if ycsb client runs as a client to a
   * GemFire cache server
   */
  private boolean isClient;

}
