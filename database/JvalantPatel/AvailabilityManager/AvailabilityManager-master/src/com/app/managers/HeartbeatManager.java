package com.app.managers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.RemoteException;

import org.tempuri.Service;
import org.tempuri.ServiceSoap;

import com.vmware.vim25.InvalidProperty;
import com.vmware.vim25.RuntimeFault;
import com.vmware.vim25.mo.AlarmManager;
import com.vmware.vim25.mo.Folder;
import com.vmware.vim25.mo.HostSystem;
import com.vmware.vim25.mo.InventoryNavigator;
import com.vmware.vim25.mo.ManagedEntity;
import com.vmware.vim25.mo.ServiceInstance;
import com.vmware.vim25.mo.VirtualMachine;
import com.app.data.InfrastructureData;
import com.app.handlers.AlarmHandler;
import com.app.handlers.RecoveryHandler;

public class HeartbeatManager extends Thread {

	ManagedEntity[] mes;
	ServiceInstance serviceinstance;
	private static HeartbeatManager instance;
}
