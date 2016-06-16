package edu.iis.mto.serverloadbalancer;

import edu.iis.mto.serverloadbalancer.Collections.ServerCollection;
import edu.iis.mto.serverloadbalancer.Collections.VmCollection;

/**
 * Created by Kamil on 2016-06-16.
 */
public class ServerLoadBalancer {

    private VmCollection vms;
    private ServerCollection servers;
    public void balance() {

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
