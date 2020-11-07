/**
 * Copyright (c) 2010 Yahoo! Inc. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You
 * may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing
 * permissions and limitations under the License. See accompanying
 * LICENSE file.
 */

package com.yahoo.ycsb.db;

import com.yahoo.ycsb.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Vector;
import java.util.Random;
import java.util.Properties;
import java.nio.ByteBuffer;

import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.cassandra.thrift.*;


//XXXX if we do replication, fix the consistency levels
/**
 * Cassandra 0.7 client for YCSB framework
 */
public class CassandraClient7 extends DB
{
  static Random random = new Random();
  public static final int Ok = 0;
  public static final int Error = -1;
  public static final ByteBuffer emptyByteBuffer = ByteBuffer.wrap(new byte[0]);

  public int ConnectionRetries;
  public int OperationRetries;
  public String column_family;

  public static final String CONNECTION_RETRY_PROPERTY = "cassandra.connectionretries";
  public static final String CONNECTION_RETRY_PROPERTY_DEFAULT = "300";

  public static final String OPERATION_RETRY_PROPERTY = "cassandra.operationretries";
  public static final String OPERATION_RETRY_PROPERTY_DEFAULT = "300";

  public static final String USERNAME_PROPERTY = "cassandra.username";
  public static final String PASSWORD_PROPERTY = "cassandra.password";

  public static final String COLUMN_FAMILY_PROPERTY = "cassandra.columnfamily";
  public static final String COLUMN_FAMILY_PROPERTY_DEFAULT = "data";

  TTransport tr;
  Cassandra.Client client;

  boolean _debug = false;
  
  String _table = "";
  Exception errorexception = null;
  
  List<Mutation> mutations = new ArrayList<Mutation>();
  Map<String, List<Mutation>> mutationMap = new HashMap<String, List<Mutation>>();
  Map<ByteBuffer, Map<String, List<Mutation>>> record = new HashMap<ByteBuffer, Map<String, List<Mutation>>>();
  
  ColumnParent parent;

  /*
   * public static void main(String[] args) throws TException,
   * InvalidRequestException, UnavailableException,
   * UnsupportedEncodingException, NotFoundException {
   * 
   * 
   * 
   * String key_user_id = "1";
   * 
   * 
   * 
   * 
   * client.insert("Keyspace1", key_user_id, new ColumnPath("Standard1", null,
   * "age".getBytes("UTF-8")), "24".getBytes("UTF-8"), timestamp,
   * ConsistencyLevel.ONE);
   * 
   * 
   * // read single column ColumnPath path = new ColumnPath("Standard1", null,
   * "name".getBytes("UTF-8"));
   * 
   * System.out.println(client.get("Keyspace1", key_user_id, path,
   * ConsistencyLevel.ONE));
   * 
   * 
   * // read entire row SlicePredicate predicate = new SlicePredicate(null, new
   * SliceRange(new byte[0], new byte[0], false, 10));
   * 
   * ColumnParent parent = new ColumnParent("Standard1", null);
   * 
   * List<ColumnOrSuperColumn> results = client.get_slice("Keyspace1",
   * key_user_id, parent, predicate, ConsistencyLevel.ONE);
   * 
   * for (ColumnOrSuperColumn result : results) {
   * 
   * Column column = result.column;
   * 
   * System.out.println(new String(column.name, "UTF-8") + " -> " + new
   * String(column.value, "UTF-8"));
   * 
   * }
   * 
   * 
   * 
   * 
   * }
   */
}
