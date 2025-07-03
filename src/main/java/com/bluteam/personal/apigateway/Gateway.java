package com.bluteam.personal.apigateway;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class Gateway {

    ConcurrentMap<String, Service> services = new ConcurrentHashMap<>();

    public Service routeRequest(String url) {
        String[] split = url.split("/");
        StringBuilder urlBuilder = new StringBuilder();
        urlBuilder.append("/").append(split[1]).append("/").append("*");

        return services.get(urlBuilder.toString());
    }

    public void registerService(Service service) {
        services.put(service.getUrl(), service);
    }
}
