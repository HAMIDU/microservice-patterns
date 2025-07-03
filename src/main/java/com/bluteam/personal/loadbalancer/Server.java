package com.bluteam.personal.loadbalancer;


public class Server {

    int id;
    String name;
    String ip;
    int port;

    public Server(int id, String name, String ip, int port) {
        this.id = id;
        this.name = name;
        this.ip = ip;
        this.port = port;
    }

    public int getId() {
        return id;
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
