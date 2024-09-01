
package middleware.dataaccess;

import java.sql.ResultSet;

import middleware.DatabaseException;
import middleware.externalinterfaces.IDbClass;

/**
 * @author pcorazza
 * @since Nov 10, 2004
 * Class Description:
 * 
 * 
 */
class DbAction {
    protected String query;
    protected ResultSet resultSet;
    protected IDbClass concreteDbClass;

}
