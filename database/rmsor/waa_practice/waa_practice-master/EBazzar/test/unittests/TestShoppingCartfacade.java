package unittests;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import business.productSubsystem.ProductSubsystemFacade;
import business.shoppingCartSubsystem.ShoppingCartSubsystemFacade;
import business.subsystemExternalInterfaces.Catalog;
import business.subsystemExternalInterfaces.Product;
import business.subsystemExternalInterfaces.ProductSubsystem;
import business.subsystemExternalInterfaces.ShoppingCart;


public class TestShoppingCartfacade {
	ShoppingCart testCart = new ShoppingCartSubsystemFacade();
	ProductSubsystem prodSubsystem = new ProductSubsystemFacade();	
	Catalog books;
	Product goneWind;
	Product coreJava;
	Product umlDistilled;
	

	  public final static double TESTCARTCOST = 1500;
	  public final static double ERRORMARGIN = .001;


}
