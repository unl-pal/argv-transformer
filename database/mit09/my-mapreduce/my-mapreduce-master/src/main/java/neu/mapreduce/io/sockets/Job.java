package neu.mapreduce.io.sockets;

import java.util.ArrayList;
import java.util.HashSet;

/**
 *
 * Created by Mit, Amitash, Srikar on 4/19/15.
 * This is used by master scheduler to keep track of slaves that have been
 * used as mappers and reducers
 */
public class Job {


    private Long jobID;
    private ArrayList<String> mapperSlaveID;
    private ArrayList<String> reducerSlaveID;

}
