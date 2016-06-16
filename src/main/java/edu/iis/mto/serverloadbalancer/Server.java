package edu.iis.mto.serverloadbalancer;

import edu.iis.mto.serverloadbalancer.Collections.VmCollection;
import edu.iis.mto.serverloadbalancer.Collections.VmCollectionBuilder;

public class Server {
    private int capacity;
    private double load;
    private VmCollection vms;

    Server(){
        vms = VmCollectionBuilder.Empty();
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public double getLoad() {
        return load;
    }

    public void setLoad(double inputLoad) {
        load = inputLoad;
    }

    public VmCollection getVms() {
        return vms;
    }

    public void addVm(Vm vm){
        vms.add(vm);
        double consumedLoad = ((double)vm.getSize()/(double)getCapacity())*100.0d;
        setLoad(getLoad()+consumedLoad);
    }

    public void removeVm(Vm vm){
        vms.remove(vm);
        double freedLoad = (vm.getSize()/getCapacity())*100.0d;
        setLoad(getLoad()-freedLoad);
    }

    public void setVms(VmCollection vms) {
        this.vms = vms;
    }
}
