package neu.mapreduce.io.sockets;

/**
 * Created by Amitash, Srikar on 4/20/15.
 */
public interface Message {

    public static final String SEND_KEY_MAPPING_FILE_MESSAGE = "sendKeyMappingFile";
    public static final String RUN_REDUCE = "runReduce";
    public static final String INITIAL_GET_KEY_SHUFFLE = "sendShuffleFiles";
    public static final String SEND_SHUFFLE_FILE = "sendShuffleFile";
    public static final String FILE_SENT = "fileSent";
    public static final String JAR_RECEIVED = "jarReceived";
    public static final String INITIAL_REDUCE = "initialReduce";
    public static final String READY_FOR_JOB = "readyForJob";
    public static final String CHANGE_STATUS = "changeStatus";
    public static final String STATUS = "status";
    public static final String RUN_JOB = "runJob";
    public static final String READY_TO_RECEIVE_JAR = "readyToReceiveJar";
    public static final String IDLE = "Idle";
    public static final String COMPLETE = "Complete";
    public static final String BUSY = "Busy";
    public static final String SEND_OUTPUT = "send_output";
}
