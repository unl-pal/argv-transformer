package unittests;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import application.BrowseSelectController;
import business.productSubsystem.ProductSubsystemFacade;
import business.shoppingCartSubsystem.ShoppingCartSubsystemFacade;
import business.subsystemExternalInterfaces.Catalog;
import business.subsystemExternalInterfaces.Product;
import business.subsystemExternalInterfaces.ProductSubsystem;
import business.subsystemExternalInterfaces.ShoppingCart;
import business.subsystemExternalInterfaces.ShoppingCartLineItem;

public class TestBrowseSelectController {

	BrowseSelectController bsc;
	ShoppingCart shopCart = new ShoppingCartSubsystemFacade();
	
	private final static int NUMBER_CATALOGS = 2;
	private final static int NUMBER_BOOKS = 2;
	private List<String> catalogNames = new ArrayList();
	private List<String> productNames = new ArrayList();
	private String testCatName = "Books";
	private String testProdName = "Tom Soyer";
	private Catalog bookCat = null;
	Product prod1 = null;
	Product prod2 = null;
	Product prod3 = null;
	Product prod4 = null;

}
