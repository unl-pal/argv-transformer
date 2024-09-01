package business.shoppingCartSubsystem;

import business.productSubsystem.ProductSubsystemFacade;
import business.subsystemExternalInterfaces.Product;
import business.subsystemExternalInterfaces.ProductSubsystem;
import business.subsystemExternalInterfaces.ShoppingCartLineItem;

/**
 * For now, am assuming that product names are unique.  Might need to refactor that later.
 * @author levi
 *
 */
public class ShoppingCartLineItemImpl implements ShoppingCartLineItem {
	private Product cartProduct;
	private int quantity;
	
	
	

}
