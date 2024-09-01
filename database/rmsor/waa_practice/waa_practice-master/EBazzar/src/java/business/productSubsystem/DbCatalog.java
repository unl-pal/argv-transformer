package business.productSubsystem;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import middleware.DatabaseException;
import middleware.dataaccess.DataAccessSubsystemFacade;
import middleware.externalinterfaces.IDataAccessSubsystem;
import middleware.externalinterfaces.IDbClass;
import application.gui.CatalogListWindow;

/**
 * 
 * @author levi
 *
 */
public class DbCatalog implements IDbClass {

	/* queryType, query, and dataAccess needed for all shadow classes */
    private String queryType;  //set by target-query-method inside shadow class
    private String query;	//set by local method, called by DAO
    private IDataAccessSubsystem dataAccess;
    
    private int catId = -99;  //used as parameter in queries--i.e., get products from catalog
    
    /* fields to hold returned objects or datastructures from reads--clients need to know getters for these */
	List<String[]> catalogNameDisplayList = new ArrayList<String[]>();
	List<String[]> productListFromCatalog = new ArrayList<String[]>();

    
    private final String SAVE = "Save";
    private final String READCATALOGNAMES = "ReadCatalogNames";
    private final String READPRODUCTS = "ReadProductsFromCatalog";


}
