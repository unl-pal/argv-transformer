package unittests;


import org.junit.runner.RunWith;
import org.junit.runners.Suite;
 
@RunWith(Suite.class)
@Suite.SuiteClasses({
	TestOrderSubsystem.class,
	TestShoppingCartfacade.class,
	TestProductSubsystemFacade.class,
	TestCustomerSubsystemFacade.class,
	TestBrowseSelectController.class
})
public class BaselineSubsystemSuite {
    // the class remains completely empty, 
    // being used only as a holder for the above annotations
}