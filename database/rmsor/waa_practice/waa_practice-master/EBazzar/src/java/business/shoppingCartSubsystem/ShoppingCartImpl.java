package business.shoppingCartSubsystem;

import java.util.ArrayList;
import java.util.List;

import business.subsystemExternalInterfaces.Address;
import business.subsystemExternalInterfaces.CreditCard;
import business.subsystemExternalInterfaces.Product;
import business.subsystemExternalInterfaces.ShoppingCartLineItem;

public class ShoppingCartImpl {
	
	/* list of all the line items for this cart */
	private List<ShoppingCartLineItem> cartLineItems = new ArrayList<ShoppingCartLineItem>();
	private Address defaultShipAdd;
	private Address defaultbillAdd;
	private CreditCard defaultPayment;

}
