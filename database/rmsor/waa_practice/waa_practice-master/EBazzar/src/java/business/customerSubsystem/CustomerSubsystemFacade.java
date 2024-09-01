package business.customerSubsystem;

import java.util.Collection;
import java.util.List;

import business.subsystemExternalInterfaces.Address;
import business.subsystemExternalInterfaces.CreditCard;
import business.subsystemExternalInterfaces.Customer;
import business.subsystemExternalInterfaces.CustomerSubsystem;
import business.subsystemExternalInterfaces.Order;
import business.subsystemExternalInterfaces.ShoppingCart;

/*
 * will contain default shipping and billing addresses, as well as credit card info
 */
/**
 * @uml.dependency   supplier="business.subsystemExternalInterfaces.CreditCard"
 */
public class CustomerSubsystemFacade implements CustomerSubsystem {

	/**
	 * @uml.property   name="shoppingCart"
	 * @uml.associationEnd   inverse="customer1:business.ShoppingCartSubsystemFacade"
	 */
	private ShoppingCart shoppingCart;

	/**
	 * @uml.property  name="order"
	 * @uml.associationEnd  multiplicity="(0 -1)" inverse="customer:business.Order"
	 */
	private Collection<Order> order;

}
