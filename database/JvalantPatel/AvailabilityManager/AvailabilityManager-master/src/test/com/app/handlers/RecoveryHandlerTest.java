/**
 * 
 */
package test.com.app.handlers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.RemoteException;
import java.util.List;

import org.junit.Test;

import com.app.data.InfrastructureData;
import com.vmware.vim25.HostConnectSpec;
import com.vmware.vim25.InvalidProperty;
import com.vmware.vim25.RuntimeFault;
import com.vmware.vim25.TaskInfoState;
import com.vmware.vim25.VirtualMachineMovePriority;
import com.vmware.vim25.VirtualMachinePowerState;
import com.vmware.vim25.mo.ComputeResource;
import com.vmware.vim25.mo.Datacenter;
import com.vmware.vim25.mo.Folder;
import com.vmware.vim25.mo.HostSystem;
import com.vmware.vim25.mo.InventoryNavigator;
import com.vmware.vim25.mo.ManagedEntity;
import com.vmware.vim25.mo.ResourcePool;
import com.vmware.vim25.mo.ServiceInstance;
import com.vmware.vim25.mo.Task;
import com.vmware.vim25.mo.VirtualMachine;

/**
 * @author Jvalant
 *
 */
public class RecoveryHandlerTest {

	/*@Test
	public void getStatusOfAllvHosts() throws InvalidProperty, RuntimeFault, RemoteException {
		
		ServiceInstance instance = InfrastructureData.getInstance().getServiceInstance();
		InfrastructureData.getInstance().updateInfra();
		for(HostSystem system : InfrastructureData.getInstance().getHostSystems()){
			System.out.print("vHost Name: "+system.getName());
			System.out.println(" Status: "+system.getSummary().overallStatus);
		}
		
	}*/
	
	/*@Test
	public void getStatusOfAllvHosts() throws InvalidProperty, RuntimeFault, RemoteException {
		
		ServiceInstance instance = InfrastructureData.getInstance().getServiceInstance();
		Folder root = instance.getRootFolder();
		ManagedEntity[] mes = new InventoryNavigator(root).searchManagedEntities("Datacenter");
		Datacenter dc = (Datacenter)mes[0];
		
		
		ServiceInstance instanceAdmin = InfrastructureData.getInstance().getAdminServiceInstance();
		Folder rootAdmin = instanceAdmin.getRootFolder();
		ManagedEntity[] mesAdmin = new InventoryNavigator(rootAdmin).searchManagedEntities("ComputeResource");
		ComputeResource computeResource = (ComputeResource) mesAdmin[0];
		System.out.println(computeResource.getName());
		ResourcePool rp = computeResource.getResourcePool();
		for(int index=0;index<rp.getResourcePools().length;index++){
			if(rp.getResourcePools()[index].getName().equals("Team04_vHOSTS")){
				ResourcePool myResource = rp.getResourcePools()[index];
				//System.out.println(myResource.getVMs()[2].getName());
				for(int i=0;i<myResource.getVMs().length;i++)
				System.out.println(myResource.getVMs()[i].getName());
			}
		}
		
		HostConnectSpec hostConnectSpec = new HostConnectSpec();
		hostConnectSpec.setHostName("130.65.132.162");
		hostConnectSpec.setPassword("12!@qwQW");
		hostConnectSpec.setUserName("root");
		hostConnectSpec.setVimAccountName("root");
		hostConnectSpec.setVimAccountPassword("12!2qwQW");
		//hostConnectSpec.setSslThumbprint("0F:A1:03:D1:34:1C:A0:EC:90:2B:40:9F:3E:CD:F6:10:36:9A:12:2D");
		
		
		
		Task task = dc.getHostFolder().addStandaloneHost_Task(hostConnectSpec, null, false);
		
		while(task.getTaskInfo().getState().equals("running")){};
		hostConnectSpec.sslThumbprint = task.getTaskInfo().error.fault.;
		dc.getHostFolder().
		
		hostConnectSpec.vmFolder
	}*/
	
	@SuppressWarnings("unused")
	@Test
	public void powerOnvHost() throws InvalidProperty, RuntimeFault, RemoteException, InterruptedException {
		
		ServiceInstance instance = InfrastructureData.getInstance().getServiceInstance();
		Folder root = instance.getRootFolder();
		ManagedEntity[] mes = new InventoryNavigator(root).searchManagedEntities("HostSystem");
		for(int index=0;index<mes.length;index++){
			HostSystem vHost = (HostSystem)mes[index];
			if(vHost.getName().contains("162")){
				//System.out.println(vHost.getName());
				VirtualMachine vm = getvHostFromAdminVCenter("162");
				System.out.println(vm.getName());
				Task task = vm.powerOnVM_Task(null);
				
				while (task.getTaskInfo().state == task.getTaskInfo().state.running) {
					System.out.print(".");
				}
				
				if(vm.getSummary().runtime.powerState == vm.getSummary().runtime.powerState.poweredOn){
					System.out.println("vHost is powered on now...");
					
				}
				
				System.out.println("waiting for vHost to be available");
				
				//while(!pingVirtualMachine("130.65.132.162"));
				
				
				
				System.out.println("vHost is available now");
						
				System.out.println("Trying to reconnect host...");
				for(int i=0;i<15;i++){
					System.out.println("attempt - "+i);
				Task taskvm = vHost.reconnectHost_Task(null);
			
				
				while (taskvm.getTaskInfo().state == taskvm.getTaskInfo().state.running) {
					System.out.print(".");
				}
				
				System.out.println(vHost.getRuntime().getConnectionState().equals("connected"));
				
				if(taskvm.getTaskInfo().state == taskvm.getTaskInfo().state.success)
					System.out.println(vHost.getRuntime().getConnectionState().equals("connected"));
					break;
				}
					
			}		
		}
	}
	
	/*@Test
	public void getPowerStatus() throws InvalidProperty, RuntimeFault, RemoteException {
		
		ServiceInstance instance = InfrastructureData.getInstance().getServiceInstance();
		Folder root = instance.getRootFolder();
		ManagedEntity[] mes = new InventoryNavigator(root).searchManagedEntities("HostSystem");
		ManagedEntity[] mvms = new InventoryNavigator(root).searchManagedEntities("VirtualMachine");
		ManagedEntity[] resourcePools = new InventoryNavigator(root).searchManagedEntities("ResourcePool");
			ResourcePool rp = (ResourcePool) resourcePools[0];
			System.out.println(rp.getName());
			HostSystem vHost = (HostSystem)mes[1];
			System.out.println(vHost.getName());
			
			VirtualMachine vmVhost = getvHostFromAdminVCenter("162");
		ComputeResource cr = (ComputeResource)vHost.getParent();
		for(int index=0;index<mvms.length;index++){
			VirtualMachine vm = (VirtualMachine) mvms[index];

			if(vm.getName().equals("user2-VM")){
         System.out.println(vm.getSummary().runtime.powerState);				
				vm.migrateVM_Task(cr.getResourcePool(), vHost, VirtualMachineMovePriority.highPriority, VirtualMachinePowerState.poweredOff);
		         System.out.println(cr.getResourcePool().getName());		
				vm.revertToCurrentSnapshot_Task(null);
				vm.powerOnVM_Task(null);
				break; 	
			}
			
			
			
		}
	
		
		
	}*/
	

}
