package middleware.dataaccess;
import java.util.LinkedList;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.HashMap;
import java.util.Queue;
import java.util.logging.Logger;
import static middleware.StringParse.*;

import middleware.DatabaseException;
import middleware.externalinterfaces.Cleanup;

//remove this import statement for jdk 1.4
//import java.util.Queue;

/** this replaces DbConnection */
class SimpleConnectionPool {
    
    // maps URI -> free connections queue 
    private HashMap<String,Queue<Connection>> freeConnectionsMap = new HashMap<String,Queue<Connection>>();
	
    // maps URI -> numclients (Integer)
    private static HashMap<String,Integer> numClientsMap = new HashMap<String,Integer>(); 
    private static Logger log = Logger.getLogger(SimpleConnectionPool.class.getPackage().getName());
    private final String DEFAULT_DRIVER = "sun.jdbc.odbc.JdbcOdbcDriver";

    private static SimpleConnectionPool instance;

    private String dbuser;

    private String dbpass;

    private String drivername;

    private int maxconn;

}
