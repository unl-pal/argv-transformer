package unittests;


import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import business.orderSubsystem.OrderSubsystemFacade;
import business.productSubsystem.ProductSubsystemFacade;
import business.shoppingCartSubsystem.ShoppingCartSubsystemFacade;
import business.subsystemExternalInterfaces.Catalog;
import business.subsystemExternalInterfaces.Order;
import business.subsystemExternalInterfaces.OrderSubsystem;
import business.subsystemExternalInterfaces.Product;
import business.subsystemExternalInterfaces.ProductSubsystem;
import business.subsystemExternalInterfaces.ShoppingCart;

public class TestOrderSubsystem {
	

	private OrderSubsystem orderSubsystem = new OrderSubsystemFacade();
	private ShoppingCart cart = new ShoppingCartSubsystemFacade();
	private ProductSubsystem prodSubsystem = new ProductSubsystemFacade();
	private String custId = "1";
	private int numOfOrders = 0;
	private List<Order> orderList = null;
	private String orderId;
	private static final double DELTA_MARGIN = .0001;
	
	
}
