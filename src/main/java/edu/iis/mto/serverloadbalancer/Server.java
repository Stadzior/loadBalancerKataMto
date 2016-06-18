package edu.iis.mto.serverloadbalancer;


public class Server {

    private static final double MAXIMUM_LOAD = 100.0d;
    public double currentLoadPecentage;
	public int capacity;

	public Server(int capacity) {
		this.capacity = capacity;
	}

	public boolean contains(Vm vm) {
		return true;
	}

    public void addVm(Vm vm) {
        currentLoadPecentage = (double) vm.size / (double) capacity * MAXIMUM_LOAD;
    }
}
