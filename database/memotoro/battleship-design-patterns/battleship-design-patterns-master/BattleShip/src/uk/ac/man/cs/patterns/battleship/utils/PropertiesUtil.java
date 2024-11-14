package uk.ac.man.cs.patterns.battleship.utils;

import java.util.Properties;

/**
 * Singleton Patterns. PropertiesUtil is a class useful to read information from the files by key.
 * Use singleton pattern in order to avoid more that one instance of the class have access to the file.
 * @author Guillermo Antonio Toro Bayona
 */
public class PropertiesUtil {

    /**
     * Properties to read file by key.
     */
    private Properties properties;
    /**
     * Static variable of the same type of the class. Feature of singleton.
     */
    private static PropertiesUtil propertiesUtil;
}
