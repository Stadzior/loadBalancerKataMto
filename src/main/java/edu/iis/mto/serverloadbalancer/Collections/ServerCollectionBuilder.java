package edu.iis.mto.serverloadbalancer.Collections;

import edu.iis.mto.serverloadbalancer.Server;
import edu.iis.mto.serverloadbalancer.ServerLoadBalancerBuilder;

public class ServerCollectionBuilder {

    private ServerCollection servers;

    public ServerCollectionBuilder(){
        servers = new ServerCollection();
    }

    public ServerCollectionBuilder withServer(Server server) {
        servers.add(server);
        return this;
    }

    public ServerCollection build(){
        return servers;
    }
}
