package application;


import java.util.List;

import business.subsystemExternalInterfaces.CustomerSubsystem;
import business.subsystemExternalInterfaces.Order;
import business.subsystemExternalInterfaces.OrderSubsystem;

public class ViewOrderController {

	/**
				 * @uml.property  name="customerSubsystem"
				 * @uml.associationEnd  inverse="viewOrderController:business.subsystemExternalInterfaces.CustomerSubsystem"
				 */
				private CustomerSubsystem customerSubsystem;
				/**
				 * @uml.property  name="orderSubsystem"
				 * @uml.associationEnd  inverse="viewOrderController:business.subsystemExternalInterfaces.OrderSubsystem"
				 */
				private OrderSubsystem orderSubsystem;

}
