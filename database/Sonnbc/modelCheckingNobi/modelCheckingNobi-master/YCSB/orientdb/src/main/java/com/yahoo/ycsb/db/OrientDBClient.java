/**
 * OrientDB client binding for YCSB.
 *
 * Submitted by Luca Garulli on 5/10/2012.
 *
 */

package com.yahoo.ycsb.db;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import java.util.Vector;

import com.orientechnologies.orient.core.config.OGlobalConfiguration;
import com.orientechnologies.orient.core.db.document.ODatabaseDocumentTx;
import com.orientechnologies.orient.core.dictionary.ODictionary;
import com.orientechnologies.orient.core.intent.OIntentMassiveInsert;
import com.orientechnologies.orient.core.record.ORecordInternal;
import com.orientechnologies.orient.core.record.impl.ODocument;
import com.yahoo.ycsb.ByteIterator;
import com.yahoo.ycsb.DB;
import com.yahoo.ycsb.DBException;
import com.yahoo.ycsb.StringByteIterator;

/**
 * OrientDB client for YCSB framework.
 * 
 * Properties to set:
 * 
 * orientdb.url=local:C:/temp/databases or remote:localhost:2424 <br>
 * orientdb.database=ycsb <br>
 * orientdb.user=admin <br>
 * orientdb.password=admin <br>
 * 
 * @author Luca Garulli
 * 
 */
public class OrientDBClient extends DB {

  private ODatabaseDocumentTx             db;
  private static final String             CLASS = "usertable";
  private ODictionary<ORecordInternal<?>> dictionary;
}
