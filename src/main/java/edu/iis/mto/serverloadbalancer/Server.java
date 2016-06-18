package edu.iis.mto.serverloadbalancer;

import java.util.ArrayList;
import java.util.List;

public class Server {

	public static final double MAXIMUM_LOAD = 100.0d;
    private double currentLoadPercentage;
	private int capacity;
	private List<Vm> vms = new ArrayList<Vm>();

	public Server(int capacity, double load) {
		this.capacity = capacity;
        this.currentLoadPercentage = load;
	}

	public boolean contains(Vm vm) {
		return vms.contains(vm);
	}

	public void addVm(Vm vm) {
		this.vms.add(vm);
		this.currentLoadPercentage = (double) vm.size / (double) this.capacity
				* MAXIMUM_LOAD;
	}

	public int vmsCount() {
		return vms.size();
	}

    public double getCurrentLoadPercentage() {
        return currentLoadPercentage;
    }

    public void setCurrentLoadPercentage(double currentLoadPercentage) {
        this.currentLoadPercentage = currentLoadPercentage;
    }
}
