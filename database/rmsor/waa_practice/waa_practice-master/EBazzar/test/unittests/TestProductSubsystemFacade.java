package unittests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.After;
import org.junit.Test;

import business.productSubsystem.ProductSubsystemFacade;
import business.subsystemExternalInterfaces.Catalog;
import business.subsystemExternalInterfaces.Product;

/**
 * JUnit test class for the Product Subsystem. 
 * 
 * @author Stefan Minkov
 * @date May 23, 2007
 */
public class TestProductSubsystemFacade {
	
	private final String[] testCatalogNames = {"TestCat1","TestCat2","TestCat3"};
	@Test
	/*
	 * We test that function by creating a couple of new catalogs and
	 * trying to find them among the set of catalogs returned by te method call
	 */
	public void testGetCatalogNames() {
		ProductSubsystemFacade productSubsystem = new ProductSubsystemFacade();
		for(int i = 0; i < testCatalogNames.length; i++)
			productSubsystem.createCatalog(testCatalogNames[i]);
		
		List<String> catalogNames = productSubsystem.getCatalogNames();
		if (catalogNames == null)
			fail("GetCatalogNames failed--returned null");
		for(int i = 0; i < testCatalogNames.length; i++)
			assertTrue("GetCatalogNames failed",catalogNames.contains(testCatalogNames[i]));
	}

	@Test
	/*
	 * Test the deleteCatalog method.
	 */
	public void deleteCatalog() {
		ProductSubsystemFacade productSubsystem = new ProductSubsystemFacade();
		int catalogCount = 0;
		List<String> catalogNames = productSubsystem.getCatalogNames();
		if (catalogNames != null)
			catalogCount = catalogNames.size();
		for(int i = 0; i < testCatalogNames.length; i++){
			Catalog tmpCatalog = productSubsystem.createCatalog(testCatalogNames[i]);
			productSubsystem.deleteCatalog(tmpCatalog);
		}
		catalogNames = productSubsystem.getCatalogNames();
		if (catalogNames == null){
			if (catalogCount != 0) 
				fail("deleteCatalog failed");
		}
		else{
			assertEquals(catalogCount,catalogNames.size());
			for(int i = 0; i < testCatalogNames.length; i++){
				assertFalse(catalogNames.contains(testCatalogNames[i]));
			}
		}
	}
	

}
