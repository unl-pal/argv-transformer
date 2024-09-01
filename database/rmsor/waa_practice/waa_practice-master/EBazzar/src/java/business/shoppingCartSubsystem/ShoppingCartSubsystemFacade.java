package business.shoppingCartSubsystem;

import business.productSubsystem.ProductImpl;
import business.subsystemExternalInterfaces.Address;
import business.subsystemExternalInterfaces.CreditCard;
import business.subsystemExternalInterfaces.CustomerSubsystem;
import business.subsystemExternalInterfaces.Product;
import business.subsystemExternalInterfaces.ShoppingCart;
import business.subsystemExternalInterfaces.ShoppingCartLineItem;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class ShoppingCartSubsystemFacade implements ShoppingCart {


	/** 
	 * @uml.property name="customer1"
	 * @uml.associationEnd multiplicity="(1 1)" inverse="shoppingCart:business.CustomerSubsystemFacade"
	 */
	private CustomerSubsystem customerSubsys;
	private ShoppingCartImpl cartImplementation = new ShoppingCartImpl();

}
