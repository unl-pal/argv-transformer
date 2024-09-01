package com.app.managers;

import com.app.data.InfrastructureData;
import com.app.handlers.SnapshotHandler;
import com.vmware.vim25.mo.ServiceInstance;

public class SnapshotManager extends Thread {
	SnapshotHandler snapShothandler;
	ServiceInstance serviceInstance;
	ServiceInstance adminServiceInstance;
	private static SnapshotManager instance;

}
