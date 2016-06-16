package edu.iis.mto.serverloadbalancer.Collections;

import edu.iis.mto.serverloadbalancer.Vm;

/**
 * Created by Kamil on 2016-06-16.
 */
public class VmCollectionBuilder {

    private VmCollection vms;

    public VmCollectionBuilder(){
        vms = new VmCollection();
    }

    public static VmCollection Empty(){
        return new VmCollection();
    }

    public VmCollectionBuilder withVm(Vm vm) {
        vms.add(vm);
        return this;
    }

    public VmCollection build() {
        return vms;
    }
}
