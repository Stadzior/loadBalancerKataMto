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
            try {
                findLeastLoadedServer(findServersWithEnoughCapacity(servers, vm)).addVm(vm);
            }catch(ThereIsNoCapableServerForThisVmException e){
                System.out.println(e.getMessage());
            }
        }
    }

    private ServerCollection findServersWithEnoughCapacity(ServerCollection servers, Vm vm) throws ThereIsNoCapableServerForThisVmException {

        double sizeLeftOnServer;
        boolean serverHasEnoughCapacity;

        ServerCollection capableServers = new ServerCollection();

        for(Server server : servers){

            sizeLeftOnServer = (100.0d - server.getLoad())*server.getCapacity();
            serverHasEnoughCapacity = sizeLeftOnServer>vm.getSize();

            if(serverHasEnoughCapacity)
                capableServers.add(server);
        }
        if (capableServers.size() == 0)
            throw new ThereIsNoCapableServerForThisVmException(vm);
        return capableServers;
    }

    private Server findLeastLoadedServer(ServerCollection servers) {

        Server leastLoadedServer = servers.get(0);

        for(Server server : servers){

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
