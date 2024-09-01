
package middleware.dataaccess;

import java.sql.Connection;
import java.sql.ResultSet;
//import business.DbUrl;

import middleware.DatabaseException;

/**
 * @author pcorazza
 * @since Nov 10, 2004
 * Class Description:
 * 
 * 
 */
public class DataAccessUtil {
    /// utilities for getting next id in various tables
    final static String custQuery =  "SELECT DISTINCT custid "+
	   								"FROM Customer ";
    final static String shopCartQuery  = "SELECT DISTINCT cartid "+
	   									"FROM ShopCartTbl ";
    final static String cartItemQuery =  "SELECT DISTINCT lineitemid "+
											"FROM ShopCartItems ";
    final static String orderQuery =  "SELECT DISTINCT orderid "+
											"FROM [Order] ";
    final static String orderItemQuery =  "SELECT DISTINCT lineitemid "+
    								"FROM OrderItem ";
    
   	final static String productIdQuery =  
   	    "SELECT DISTINCT productid "+
   	    "FROM Product ";
}
