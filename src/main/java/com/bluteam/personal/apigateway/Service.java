package com.bluteam.personal.apigateway;

public class Service {

    String name;
    String url;

    public Service(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }
}

