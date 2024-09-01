/**
 * 
 */
/** filtered and transformed by PAClab */
package test.com.app.handlers;

import org.sosy_lab.sv_benchmarks.Verifier;

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
	
	/** PACLab: suitable */
	 public void powerOnvHost() throws Exception {
		
		Random rand = new Random();
		for(int index=0;index<Verifier.nondetInt();index++){
			if(rand.nextBoolean()){
				while (rand.nextBoolean()) {
				}
				
				if(vm.getSummary().runtime.powerState == vm.getSummary().runtime.powerState.poweredOn){
					
				}
				
				for(int i=0;i<15;i++){
					while (rand.nextBoolean()) {
				}
				
				if(taskvm.getTaskInfo().state == taskvm.getTaskInfo().state.success) {
				}
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
