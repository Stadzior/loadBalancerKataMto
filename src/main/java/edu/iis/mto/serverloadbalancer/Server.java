package edu.iis.mto.serverloadbalancer;

/**
 * Created by Kamil on 2016-06-16.
 */
public class Server {
    private int capacity;
    private double load;
    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public double getLoad() {
        return load;
    }
}
