package edu.iis.mto.serverloadbalancer;

import edu.iis.mto.serverloadbalancer.Collections.ServerCollection;
import edu.iis.mto.serverloadbalancer.Collections.VmCollection;

import java.util.ArrayList;

public class ServerLoadBalancerBuilder {
    private ServerLoadBalancer loadBalancer;

    ServerLoadBalancerBuilder(){
        loadBalancer = new ServerLoadBalancer();
    }

    ServerLoadBalancerBuilder withVms(VmCollection vms){
        loadBalancer.setVms(vms);
        return this;
    }

    public ServerLoadBalancerBuilder withServers(ServerCollection servers) {
        loadBalancer.setServers(servers);
        return this;
    }

    ServerLoadBalancer build(){
        return loadBalancer;
    }


}
