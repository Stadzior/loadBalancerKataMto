package edu.iis.mto.serverloadbalancer;

public class ServerLoadBalancer {

	public void balance(Server[] servers, Vm[] vms) {
		for (Vm vm : vms) {
			getLeastLoadedServer(servers).addVm(vm);
		}
	}

    private Server getLeastLoadedServer(Server[] servers) {
        Server leastLoadedServer = servers[0];
        for(Server server : servers){
            if(server.getCurrentLoadPercentage() < leastLoadedServer.getCurrentLoadPercentage())
                leastLoadedServer = server;
        }
        return leastLoadedServer;
    }

}
