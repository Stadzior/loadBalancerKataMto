package edu.iis.mto.serverloadbalancer;

/**
 * Created by Kamil on 2016-06-18.
 */
public class Server {
    private int capacity;
    private double load;

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getCapacity() {
        return capacity;
    }

    public double getLoad() {
        return load;
    }

    public void setLoad(double load) {
        this.load = load;
    }
}
