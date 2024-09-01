package application;


import business.shoppingCartSubsystem.ShoppingCartSubsystemFacade;
import business.subsystemExternalInterfaces.Catalog;
import business.subsystemExternalInterfaces.Product;
import business.subsystemExternalInterfaces.ProductSubsystem;
import business.subsystemExternalInterfaces.ShoppingCart;
import business.subsystemExternalInterfaces.ShoppingCartLineItem;

import java.util.ArrayList;
import java.util.List;

//import unittests.TestBrowseSelectController;

public class BrowseSelectController {

	/**
	 * @uml.property  name="productSubsystem"
	 * @uml.associationEnd  inverse="browseSelectController:business.subsystemExternalInterfaces.ProductSubsystem"
	 */
	private ProductSubsystem productSubsystem = null;
	

	Catalog cat2Browse = null;  //catalog being browsed
  
	/**
	 * @uml.property  name="shoppingCart"
	 * @uml.associationEnd  readOnly="true"
	 */
	private ShoppingCart shoppingCart;  //cart being used





}
