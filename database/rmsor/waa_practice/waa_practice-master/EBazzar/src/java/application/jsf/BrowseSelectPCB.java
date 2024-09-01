    /*
 * This is a Presentation Control Bean for the browse and select use case.  It
 * is intended to hold JSF action methods for the Browse and 
 * Select use case.
 * 
 * It will also hold fields accessed by the JSF pages.
 * 
 * @since 8/4/08 -- had to add the imports
 * @author levi
 */

package application.jsf;

import application.BrowseSelectController;
import business.productSubsystem.MPTestProductSubsystemFacade;
import business.subsystemExternalInterfaces.Product;
import business.subsystemExternalInterfaces.ProductSubsystem;
import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;


@Named("bsControllerPCB")
@SessionScoped
public class BrowseSelectPCB implements Serializable{
    ProductSubsystem stubProdSS = new MPTestProductSubsystemFacade();

    BrowseSelectController bsController = new BrowseSelectController(stubProdSS);
    
    List<String> catalogNames;
    List<Product> products;
    List<String[]> cart;
    
    private String selectedCatalog = "";
    private String selectedProductStr;
    private Product selectedProduct;
    private int quantity;

}

