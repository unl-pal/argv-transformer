package business.productSubsystem;

import business.subsystemExternalInterfaces.Catalog;
import business.subsystemExternalInterfaces.Product;




public class ProductImpl implements Product  {
	/**
	 * @uml.property  name="prodName"
	 */
	private String prodName;
	/**
	 * @uml.property  name="price"
	 */
	private double price;
	/**
	 * @uml.property  name="quantityInStock"
	 */
	private int quantityInStock;
	/**
	 * @uml.property  name="description"
	 */
	private String description;
	/** 
	 * @uml.property name="catalog"
	 * @uml.associationEnd multiplicity="(1 1)" inverse="productList:business.Catalog"
	 */
	private Catalog catalog;
	  

}
