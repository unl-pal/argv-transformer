package ufrgs.maslab.abstractsimulator.log;

/**
 * This class collects all the debug-related variables for classes in this project.<br/>
 * It has not methods, just public static fields that are accessed from within the right destination class.
 * @author Michele Roncalli <roncallim at gmail dot com>
 */
public class DebugVerbosity {

    /**
     * Global modifier. Use this if you want to change easily all the debug settings.
     */
    public static final int debug = -5;

    public static final int debugNodeVariable = 2
            + DebugVerbosity.debug;
    public static final int debugNodeFunction = 0
            + DebugVerbosity.debug;

    public static final int debugFunctionEvaluator = 0
            + DebugVerbosity.debug;

    public static final int debugTabularFunction = 0
            + DebugVerbosity.debug;

    public static final int debugAgent = 0
            + DebugVerbosity.debug;

    public static final int debugMessageArrayDouble = 0
            + DebugVerbosity.debug;

    public static final int debugMessageFactoryArrayDouble = 0
            + DebugVerbosity.debug;

    public static final int debugMessageQArrayDouble = 0
            + DebugVerbosity.debug;

    public static final int debugMessageRArrayDouble = 0
            + DebugVerbosity.debug;
    
    public static final int debugMessageContentMessageArrayDouble = 0
            + DebugVerbosity.debug;
    
    public static final int debugMSumOperator = 0
            + DebugVerbosity.debug;

    public static final int debugOPlus_MaxSum = 0
            + DebugVerbosity.debug;

    public static final int debugOTimes_MaxSum = 0
            + DebugVerbosity.debug;

    public static final int debugCerbero = 0
            + DebugVerbosity.debug;

    public static final int debugAthena = 0
            + DebugVerbosity.debug;

    public static final int debugEris = 0
            + DebugVerbosity.debug;

    public static final int debugWalkGrid = 3
            + DebugVerbosity.debug;

    public static final int debugSARecycler = 3
            + DebugVerbosity.debug;

    public static final int debugUtils = 0
            + DebugVerbosity.debug;

    public static final int debugMain = 0
            + DebugVerbosity.debug;
    
    public static final int debugRMessageList = 0
            + DebugVerbosity.debug;

    public static final int debugScrewerUp = 3
            + DebugVerbosity.debug;

    public static final int debugClonedMSInstance = 0
            + DebugVerbosity.debug;
    public static final int debugInstanceCloner = 0
            + DebugVerbosity.debug;

    public static final int debugBoundedMaxSum = 3
            + DebugVerbosity.debug;

    public static final int debugPowerGrid = 0
            + DebugVerbosity.debug;

    public static final int debugCOP_Instance = 0
            + DebugVerbosity.debug;

    public static final int debugMessageList = 0
            + DebugVerbosity.debug;
}
