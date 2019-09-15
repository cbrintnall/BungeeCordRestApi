package com.arctaeon.restplugin.models;

public class Server {
    String name;
    String host;
    Integer port;
    String motd;

    public String getName() {
        return this.name;
    }

    public String getHost() {
        return this.host;
    }

    public Integer getPort() {
        return this.port;
    }

    public String getMotd() {
        return this.motd;
    }

    public Server(String name, String host, Integer port, String motd) {
        this.name = name;
        this.host = host;
        this.port = port;
        this.motd = motd;
    }
}
