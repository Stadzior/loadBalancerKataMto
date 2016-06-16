package edu.iis.mto.serverloadbalancer;

import edu.iis.mto.serverloadbalancer.Collections.ServerCollection;
import edu.iis.mto.serverloadbalancer.Collections.VmCollection;

public class ServerLoadBalancer {
    public static final double FULL_LOAD = 100.0d;
    public static final double NO_LOAD = 0.0d;
    private VmCollection vms;
    private ServerCollection servers;
    public void balance() {
        for(Vm vm : vms) {
                findLeastLoadedServerWithEnoughCapacity(servers, vm).addVm(vm);
        }
    }

    private Server findLeastLoadedServerWithEnoughCapacity(ServerCollection servers, Vm vm) {
        double sizeLeftOnServer;
        boolean serverHasEnoughCapacity;
        ServerCollection capableServers = new ServerCollection();
        for(Server server : servers){
            sizeLeftOnServer = (100.0d - server.getLoad())*server.getCapacity();
            serverHasEnoughCapacity = sizeLeftOnServer>vm.getSize();
            if(serverHasEnoughCapacity)
                capableServers.add(server);
        }

        return leastLoadedServer(capableServers);
    }

    private Server leastLoadedServer(ServerCollection capableServers) {
        Server leastLoadedServer = new ServerBuilder().withCapacity(0).build();
        leastLoadedServer.setLoad(FULL_LOAD);
        for(Server server : capableServers){
            if(server.getLoad()<leastLoadedServer.getLoad())
                leastLoadedServer = server;
        }
        return leastLoadedServer;
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
