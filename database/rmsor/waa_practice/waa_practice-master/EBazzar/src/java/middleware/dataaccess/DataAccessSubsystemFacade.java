
package middleware.dataaccess;

import middleware.DatabaseException;
import middleware.externalinterfaces.Cleanup;
import middleware.externalinterfaces.IDataAccessSubsystem;
import middleware.externalinterfaces.IDbClass;

/**
 * @author pcorazza
 * @since Nov 10, 2004
 * Class Description:
 * 
 * 
 */
public class DataAccessSubsystemFacade implements IDataAccessSubsystem {
    public static final int MAX_CONNECTIONS = 4;

}
