/**
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


import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import org.apache.thrift.TException;
import org.hypertable.thrift.SerializedCellsFlag;
import org.hypertable.thrift.SerializedCellsWriter;
import org.hypertable.thrift.ThriftClient;
import org.hypertable.thriftgen.Cell;
import org.hypertable.thriftgen.ClientException;
import org.hypertable.thriftgen.Key;
import org.hypertable.thriftgen.KeyFlag;
import org.hypertable.thriftgen.RowInterval;
import org.hypertable.thriftgen.ScanSpec;
import org.hypertable.thrift.SerializedCellsReader;

import com.yahoo.ycsb.ByteArrayByteIterator;
import com.yahoo.ycsb.ByteIterator;
import com.yahoo.ycsb.DBException;

/**
 * Hypertable client for YCSB framework
 */
public class HypertableClient extends com.yahoo.ycsb.DB
{
    private boolean _debug = false;
    
    private ThriftClient connection;
    private long ns;

    private String _columnFamily = "";

    public static final int OK = 0;
    public static final int SERVERERROR = -1;
    
    public static final String NAMESPACE = "/ycsb";
    public static final int THRIFTBROKER_PORT = 38080;

    //TODO: make dynamic
    public static final int BUFFER_SIZE = 4096;
}


