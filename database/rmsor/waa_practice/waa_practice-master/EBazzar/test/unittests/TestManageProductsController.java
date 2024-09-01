/**
 * 
 */
package unittests;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import application.ManageProductsController;
import business.productSubsystem.MPTestProductSubsystemFacade;
import business.subsystemExternalInterfaces.Catalog;
import business.subsystemExternalInterfaces.ProductSubsystem;

/**
 * @author apoudyal;  refactored by kl
 * This class has test cases for a few operations on the manage products controller.  There 
 * should be additional test cases for adding, editing, saving products.
 * @since 19 Sept 2007
 */
public class TestManageProductsController {
	ManageProductsController manageController; 
	ProductSubsystem mpTestProdSubsystem = new MPTestProductSubsystemFacade();
	
	/* need tests of operations for adding and editing and deleting products */

}
