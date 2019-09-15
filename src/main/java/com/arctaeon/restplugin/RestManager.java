package com.arctaeon.restplugin;

import com.arctaeon.restplugin.models.Server;
import com.google.gson.Gson;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.plugin.Plugin;

import java.net.InetSocketAddress;
import java.util.Map;
import java.util.logging.Logger;

public class RestManager {
    private Plugin parent;
    private Logger logger;

    public RestManager(Plugin parent, Logger logger) {
        this.logger = logger;
        this.parent = parent;
    }

    public String GetServer() {
        Gson gson = new Gson();

        Map<String, ServerInfo> servers = this.parent.getProxy().getServers();

        return gson.toJson(servers);
    }

    public String AddServer(String content) {
        Gson gson = new Gson();

        Server server = gson.fromJson(content, Server.class);

        ServerInfo info = this.parent.getProxy().constructServerInfo(
                server.getName(),
                new InetSocketAddress(server.getHost(), server.getPort()),
                server.getMotd(),
                false
        );

        this.parent.getProxy().getServers().put(server.getName(), info);

        this.logger.info(String.format("Added server %s", info.getName()));

        return gson.toJson(info);
    }

    public void DeleteServer(String server) {
        this.logger.info(String.format("Removing server %s", server));
        this.parent.getProxy().getServers().remove(server);
    }
}
