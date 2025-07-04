package com.bluteam.personal.loadbalance;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class LoadBalancer {

    List<Server> servers = new CopyOnWriteArrayList<>();
    AtomicInteger currentIndex = new AtomicInteger();

    public LoadBalancer() {
        currentIndex.set(0);
    }

    public void addServer(Server server) {
        servers.add(server);
    }

    public void removeServer(Server server) {
        servers.remove(server);
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
}
