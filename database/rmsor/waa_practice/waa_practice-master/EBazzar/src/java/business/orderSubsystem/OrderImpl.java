package business.orderSubsystem;

import java.util.ArrayList;
import java.util.List;

import business.subsystemExternalInterfaces.Address;
import business.subsystemExternalInterfaces.CreditCard;
import business.subsystemExternalInterfaces.Order;
import business.subsystemExternalInterfaces.OrderLineItem;
import business.subsystemExternalInterfaces.ShoppingCart;
import business.subsystemExternalInterfaces.ShoppingCartLineItem;

public class OrderImpl implements Order {
	private List<OrderLineItem> orderItems = new ArrayList<OrderLineItem>();
	private Address shippingAddress;
	private Address billingAddress;
	private CreditCard paymentCard;
	private String orderId;
	private String orderDate;



}
