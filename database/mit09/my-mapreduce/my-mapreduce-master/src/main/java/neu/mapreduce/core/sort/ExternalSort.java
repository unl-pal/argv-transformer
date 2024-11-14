package neu.mapreduce.core.sort;


import neu.mapreduce.core.factory.WriteComparableFactory;

import org.apache.commons.io.FileUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Amitash, Mit on 4/8/15.
 */
public class ExternalSort {
    private final static Logger LOGGER = Logger.getLogger(ExternalSort.class.getName());

    public static String OUTPUT_FILE_NAME = "shuffleMerge";
    int fileId;

}