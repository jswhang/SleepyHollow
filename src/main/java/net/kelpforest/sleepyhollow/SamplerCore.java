package net.kelpforest.sleepyhollow;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.management.remote.JMXConnector;

import com.sun.tools.attach.*;
import com.sun.tools.*;
import com.sun.tools.attach.VirtualMachineDescriptor;

import sun.tools.attach.HotSpotVirtualMachine;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;

import javax.management.MBeanServerConnection;
import javax.management.ObjectInstance;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

import com.sun.tools.attach.VirtualMachine;

/*
 * SamplerCore
 * @author JunSun Whang 
 */

public class SamplerCore {

	List<VirtualMachineDescriptor> vmList = null;
	
	SamplerCore(){
		initialize();
	}
	
	private void initialize() {
		vmList = VirtualMachine.list();
	}
	
	public String retrieveProcessInfoString() {
		StringBuffer sb = new StringBuffer();
		for (VirtualMachineDescriptor vmd: vmList) {
			sb.append("___" + vmd.id() + " - " + vmd.displayName() + "\n");
		}
		return sb.toString();
	}
	

	
}


