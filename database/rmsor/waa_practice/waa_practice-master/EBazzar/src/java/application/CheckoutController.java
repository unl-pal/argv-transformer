package application;

import business.AddressSanitizerBoundary;
import business.CreditVerificationBoundary;
import business.subsystemExternalInterfaces.Address;
import business.subsystemExternalInterfaces.CreditCard;
import business.subsystemExternalInterfaces.CustomerSubsystem;
import business.subsystemExternalInterfaces.ShoppingCart;

public class CheckoutController {

	/**
	 * @uml.property  name="shoppingCart"
	 * @uml.associationEnd  inverse="checkoutController:business.ShoppingCartSubsystemFacade"
	 */
	private ShoppingCart shoppingCart;

	/**
	 * @uml.property  name="creditVerificationBoundary"
	 * @uml.associationEnd  inverse="checkoutController:application.CreditVerificationBoundary"
	 */
	private CreditVerificationBoundary creditVerificationBoundary;

	/**
	 * @uml.property  name="addressSanitizerBoundary"
	 * @uml.associationEnd  inverse="checkoutController:application.AddressSanitizerBoundary"
	 */
	private AddressSanitizerBoundary addressSanitizerBoundary;

	/**
	 * @uml.property  name="customerSubsystem"
	 * @uml.associationEnd  inverse="checkoutController:business.subsystemExternalInterfaces.CustomerSubsystem"
	 */
	private CustomerSubsystem customerSubsystem;

	/**
	 * @uml.property  name="liveCart"
	 * @uml.associationEnd  inverse="checkoutController:business.subsystemExternalInterfaces.ShoppingCart"
	 */
	private ShoppingCart liveCart;

}
