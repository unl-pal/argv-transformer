/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yahoo.ycsb.db;

import com.yahoo.ycsb.ByteIterator;
import com.yahoo.ycsb.DBException;
import com.yahoo.ycsb.StringByteIterator;
import java.util.HashMap;
import java.util.Set;
import java.util.Vector;
import static org.testng.AssertJUnit.*;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *
 * @author saden
 */
public class ElasticSearchClientTest {

    protected final static ElasticSearchClient instance = new ElasticSearchClient();
    protected final static HashMap<String, ByteIterator> MOCK_DATA;
    protected final static String MOCK_TABLE = "MOCK_TABLE";
    protected final static String MOCK_KEY0 = "0";
    protected final static String MOCK_KEY1 = "1";
    protected final static String MOCK_KEY2 = "2";

    static {
        MOCK_DATA = new HashMap<String, ByteIterator>(10);
        for (int i = 1; i <= 10; i++) {
            MOCK_DATA.put("field" + i, new StringByteIterator("value" + i));
        }
    }
}
