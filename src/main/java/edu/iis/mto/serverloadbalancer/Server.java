package edu.iis.mto.serverloadbalancer;

import java.util.ArrayList;
import java.util.List;

public class Server {

	private static final double MAXIMUM_LOAD = 100.0d;
	public double currentLoadPercentage;
	private int capacity;
    private List<Vm> vms;

    public Server(int capacity) {
		this.capacity = capacity;
        this.vms = new ArrayList<Vm>();
	}

	public boolean contains(Vm vm) {
		return true;
	}

	public void addVm(Vm vm) {
        vms.add(vm);
		this.currentLoadPercentage = (double) vm.size / (double) this.capacity
				* MAXIMUM_LOAD;
	}

    public int getVmCount() {
        return vms.size();
    }
}
