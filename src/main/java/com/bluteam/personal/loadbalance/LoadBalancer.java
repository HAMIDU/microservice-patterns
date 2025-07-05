package com.bluteam.personal.loadbalance;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class LoadBalancer {

    List<Server> servers = new CopyOnWriteArrayList<>();
    AtomicInteger currentIndex = new AtomicInteger();
    List<Server> weightedServers = new CopyOnWriteArrayList<>();

    public LoadBalancer() {
        currentIndex.set(0);
    }

    public void addServer(Server server) {
        servers.add(server);

    }

    public void addServerWithWeight(Server server) {
        weightedServers.add(server);
    }

    public void duplicatedBasedOnWeight(){
        for (Server weightedServer : weightedServers) {
            for (int i = 0; i < weightedServer.getWeight(); i++) {
                weightedServers.add(weightedServer);
            }
        }
    }
    public void removeServer(Server server) {
        servers.remove(server);
        weightedServers.remove(server);
    }

    //the round-robin
    public Server chooseServer() {
        if (servers.size() == 0) {
            return null;
        }
        int index = currentIndex.getAndIncrement() % servers.size();
        System.out.println("current index " + index);

        return servers.get(index);
    }

    //the round-robin with weight
    public Server chooseServerBasedOnWeight() {
        if (weightedServers.size() == 0) {
            return null;
        }
        int index = currentIndex.getAndIncrement() % weightedServers.size();
        return weightedServers.get(index);
    }
}
