package edu.iis.mto.serverloadbalancer;

public class ServerBuilder implements Builder<Server> {

	private int capacity;
    private double load;

    public ServerBuilder withCapacity(int capacity) {
		this.capacity = capacity;
		return this;
	}

	public Server build() {
		return new Server(capacity,load);
	}

	public static ServerBuilder server() {
		return new ServerBuilder();
	}

    public ServerBuilder withCurrentLoadOf(double load) {
        this.load = load;
        return this;
    }
}
