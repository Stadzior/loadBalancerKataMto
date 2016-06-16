package edu.iis.mto.serverloadbalancer;

/**
 * Created by Kamil on 2016-06-16.
 */
public class ServerBuilder {

    private Server server;

    ServerBuilder(){
        server = new Server();
    }
    ServerBuilder withCapacity(int capacity){
        server.setCapacity(capacity);
        return this;
    }

    Server build(){
        return server;
    }
}
