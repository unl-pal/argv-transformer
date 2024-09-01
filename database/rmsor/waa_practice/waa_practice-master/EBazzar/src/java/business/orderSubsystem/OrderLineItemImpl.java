package business.orderSubsystem;

import business.subsystemExternalInterfaces.OrderLineItem;
import business.subsystemExternalInterfaces.Product;

public class OrderLineItemImpl implements OrderLineItem {
	private Product orderProduct;
	private int quantity;

}
