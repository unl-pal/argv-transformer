/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pbash.r2o.util;

import java.util.Properties;

/**
 *
 * @author bash
 */
public class DBConfig {

   static ConfigFileLoader fileLoader = new ConfigFileLoader("dbconfig.properties");
   public static Properties dbProperties = fileLoader.getProps();

    public static String driver = dbProperties.getProperty("db1.driver");
    public static String url = dbProperties.getProperty("db1.url");
    public static String user = dbProperties.getProperty("db1.username");
    public static String password = dbProperties.getProperty("db1.password");
}
