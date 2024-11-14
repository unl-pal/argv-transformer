package neu.mapreduce.io.fileSplitter;

import neu.mapreduce.io.sockets.Constants;

import java.io.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Amitash, Srikar on 3/31/15.
 */
public class SplitFile {
    public static final String PATH_MASTER_FOLDER = Constants.HOME + Constants.USER + Constants.MR_RUN_FOLDER + Constants.MASTER_FOLER;
    public static final int BYTE_CONVERSION = 1024;
    private final static Logger LOGGER = Logger.getLogger(SplitFile.class.getName());

    private int splitSizeInMB;
}