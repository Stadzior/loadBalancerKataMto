package edu.iis.mto.serverloadbalancer;

/**
 * Created by Kamil on 2016-06-16.
 */
public class VmBuilder {
    private Vm vm;

    VmBuilder(){
        vm = new Vm();
    }


    VmBuilder withSize(int size){
        vm.setSize(size);
        return this;
    }

    Vm build(){
        return vm;
    }
}
