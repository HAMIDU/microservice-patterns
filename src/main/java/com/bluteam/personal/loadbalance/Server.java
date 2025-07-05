package com.bluteam.personal.loadbalance;


public class Server {

    int id;
    String name;
    String ip;
    int port;
    int weight;

    public Server(int id, String name, String ip, int port) {
        this.id = id;
        this.name = name;
        this.ip = ip;
        this.port = port;
    }

    public Server(int id, String name, String ip, int port, int weigth) {
        this.id = id;
        this.name = name;
        this.ip = ip;
        this.port = port;
        this.weight = weigth;
    }

    public int getId() {
        return id;
    }

    public int getWeight() {
        return weight;
    }

    public String getName() {
        return name;
    }

    public String getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }

    public String toString() {
        return getName() + " (" + getIp() + ":" + getPort() + ")";
    }
}
