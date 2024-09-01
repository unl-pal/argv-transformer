package business.productSubsystem;

import business.subsystemExternalInterfaces.Catalog;
import business.subsystemExternalInterfaces.Product;
import business.subsystemExternalInterfaces.ProductSubsystem;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Have started some implementation on this in order to get the testGetCatalogNames operation
 * to work.
 * @author levi
 *
 */
public class ProductSubsystemFacade implements ProductSubsystem {

	public static final String CLOTHES = "Clothes";
	public static final String BOOKS = "Books";
	
	/** 
	 * @uml.property name="catalogsInSystem"
	 * @uml.associationEnd multiplicity="(0 -1)" ordering="true" aggregation="composite"
	 */
	private List<Catalog> catalogsInSystem = new ArrayList<Catalog>();



}
