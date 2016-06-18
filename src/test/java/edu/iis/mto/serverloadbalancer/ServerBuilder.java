package edu.iis.mto.serverloadbalancer;

/**
 * Created by Kamil on 2016-06-18.
 */
public class ServerBuilder {
    private Server server;

    public ServerBuilder() {
        server = new Server();
    }

    public ServerBuilder withCapacity(int capacity) {

        server.setCapacity(capacity);
        return this;

    }

    public Server build() {
        return server;
    }
}
