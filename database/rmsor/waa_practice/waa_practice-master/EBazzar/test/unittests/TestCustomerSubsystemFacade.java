package unittests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import business.customerSubsystem.CustomerSubsystemFacade;
import business.subsystemExternalInterfaces.Address;
import business.subsystemExternalInterfaces.CreditCard;
import business.subsystemExternalInterfaces.Customer;
import business.subsystemExternalInterfaces.CustomerSubsystem;
    

/**
 * JUnit test class for the Customer Subsystem. 
 * 
 * @author Rubia and Abeba
 * @date May 23, 2007
 */
public class TestCustomerSubsystemFacade {
	String name1="Rubia Meskeen";
	String street1="1000 N 4th Street";
	String city1="Fairfield";
	String state1="IA";
	String zip1="52557";
	
	String name2="Misrak Ararso";
	String street2="1000 N Broadway Street";
	String city2="Alexandria";
	String state2="VA";
	String zip2="52557";
	String expDate="03/10";
	String CCtype="Visa";
	String CCNum="0954671287";
	Address add1 = null;
	Address add2 = null;
	
	List<Address>adds= new LinkedList<Address>();
	CustomerSubsystem customerF=new CustomerSubsystemFacade();



}
