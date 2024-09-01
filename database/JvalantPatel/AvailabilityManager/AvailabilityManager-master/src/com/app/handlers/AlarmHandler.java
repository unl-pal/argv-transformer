package com.app.handlers;

import java.rmi.RemoteException;

import com.app.data.InfrastructureData;
import com.vmware.vim25.AlarmSetting;
import com.vmware.vim25.AlarmSpec;
import com.vmware.vim25.AlarmState;
import com.vmware.vim25.InvalidProperty;
import com.vmware.vim25.RuntimeFault;
import com.vmware.vim25.StateAlarmExpression;
import com.vmware.vim25.StateAlarmOperator;
import com.vmware.vim25.mo.Alarm;
import com.vmware.vim25.mo.AlarmManager;
import com.vmware.vim25.mo.InventoryNavigator;
import com.vmware.vim25.mo.ServiceInstance;
import com.vmware.vim25.mo.VirtualMachine;

/***
 * Provides functionality to create power status alarm and check if the power
 * status alarm is triggered.
 * 
 */
public class AlarmHandler {
	static String alarmName = "VmPowerStatus";
}
