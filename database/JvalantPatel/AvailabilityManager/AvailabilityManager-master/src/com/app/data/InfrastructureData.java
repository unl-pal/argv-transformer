package com.app.data;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import com.app.handlers.AlarmHandler;
import com.vmware.vim25.InvalidProperty;
import com.vmware.vim25.RuntimeFault;
import com.vmware.vim25.mo.Folder;
import com.vmware.vim25.mo.HostSystem;
import com.vmware.vim25.mo.InventoryNavigator;
import com.vmware.vim25.mo.ManagedEntity;
import com.vmware.vim25.mo.ServiceInstance;
import com.vmware.vim25.mo.VirtualMachine;

public class InfrastructureData {

	private  List<HostSystem>  hostSystems ;
	
	private static InfrastructureData instance ;
	private  ServiceInstance serviceInstance ;
	private  ServiceInstance adminServiceInstance ;
	
	
}
