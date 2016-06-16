package edu.iis.mto.serverloadbalancer;

import edu.iis.mto.serverloadbalancer.Collections.ServerCollection;
import edu.iis.mto.serverloadbalancer.Collections.VmCollection;

public class ServerLoadBalancer {

    private VmCollection vms;
    private ServerCollection servers;
    public void balance() {
        double sizeLeftOnServer;
        for(Vm vm : vms) {
            for (Server server : servers) {
                sizeLeftOnServer = (100.0d - server.getLoad())*server.getCapacity();
                if(sizeLeftOnServer>vm.getSize())
                    server.addVm(vm);
            }
        }
    }

    public VmCollection getVms() {
        return vms;
    }

    public void setVms(VmCollection vms) {
        this.vms = vms;
    }

    public ServerCollection getServers() {
        return servers;
    }

    public void setServers(ServerCollection servers) {
        this.servers = servers;
    }
}
