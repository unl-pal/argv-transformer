package application;

import java.util.ArrayList;
import java.util.List;

import business.subsystemExternalInterfaces.Catalog;
import business.subsystemExternalInterfaces.Product;
import business.subsystemExternalInterfaces.ProductSubsystem;


/**
 * @uml.dependency   supplier="business.Product"
 */
public class ManageProductsController {
	

	/**
	 * @uml.property  name="productSubsystem"
	 * @uml.associationEnd  inverse="ManageProductsController:business.subsystemExternalInterfaces.ProductSubsystem"
	 */
	private ProductSubsystem productSubsystem;

}
