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
                findLeastLoadedServer(findServersWithEnoughSpace(servers, vm)).addVm(vm);
            }catch(ThereIsNoCapableServerForThisVmException e){
                if(thereIsEnoughAvailableSpaceOverall(servers,vm.getSize()) && thereIsVmWhichCanBeMoved(servers,vms)) {
                    reassignVmsToFillUnusedSpace(servers, vms);
                }else {
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    private boolean thereIsVmWhichCanBeMoved(ServerCollection servers,VmCollection vms) {
        for(Server server :servers){
            for(Vm vm : vms){
                if(server.sizeLeft()>vm.getSize())
                    return true;
            }
        }
        return false;
    }

    private ServerCollection findServersWithEnoughSpace(ServerCollection servers, Vm vm) throws ThereIsNoCapableServerForThisVmException {

        boolean serverHasEnoughCapacity;

        ServerCollection capableServers = new ServerCollection();

        for(Server server : servers){

            serverHasEnoughCapacity = server.sizeLeft()>=vm.getSize();

            if(serverHasEnoughCapacity)
                capableServers.add(server);

        }
        boolean thereIsNoCapableServers = capableServers.size() == 0;

        if (thereIsNoCapableServers)
            throw new ThereIsNoCapableServerForThisVmException(vm);

        return capableServers;
    }

    private boolean thereIsEnoughAvailableSpaceOverall(ServerCollection servers, int size) {
        int freeSpace = 0;
        for(Server server : servers){
            freeSpace+=server.getCapacity()*(100.0d - server.getLoad());
        }
        return freeSpace>=size;
    }

    private void reassignVmsToFillUnusedSpace(ServerCollection servers, VmCollection vms){
        clearServers(servers);
        for(Server server : servers){
            server.setVms(findOptimalVms(server, vms));
        }
    }

    private VmCollection findOptimalVms(Server server, VmCollection vms) {
        return new VmCollection();
    }

    private void clearServers(ServerCollection servers) {
        for(Server server : servers){
            server.getVms().clear();
        }
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
