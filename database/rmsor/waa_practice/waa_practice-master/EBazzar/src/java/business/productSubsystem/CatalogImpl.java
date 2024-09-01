package business.productSubsystem;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import business.subsystemExternalInterfaces.Catalog;
import business.subsystemExternalInterfaces.Product;


public class CatalogImpl implements Catalog {
	private String catalogName;
	/** 
	 * @uml.property name="productList"
	 * @uml.associationEnd multiplicity="(1 -1)" aggregation="shared" inverse="catalog:business.Product"
	 */
	private List<Product> productList = new java.util.ArrayList();

}
