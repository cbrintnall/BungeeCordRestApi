package com.arctaeon.restplugin;

import static spark.Spark.*;
import net.md_5.bungee.api.plugin.Plugin;

import java.util.logging.Logger;

public class RestPlugin extends Plugin {
    private Thread restThread;

    // TODO: Need to disable the thread on disable

    @Override
    public void onEnable() {
        this.restThread = new Thread(new PluginThread(this, getLogger()));
        this.restThread.start();
    }

    @Override
    public void onDisable() {
        this.restThread.stop();
    }
}

class PluginThread implements Runnable {
    private RestManager manager;

    PluginThread(Plugin plugin, Logger serverLogger) {
        super();

        this.manager = new RestManager(plugin, serverLogger);
    }

    @Override
    public void run() {
        before((req, res) -> {
            if ((req.requestMethod() == "POST" || req.requestMethod() == "PUT")
                    && req.headers("Content-Type") != "application/json") {
                halt(415);
            }
        });

        after((req, res) -> {
           res.header("Content-Type", "application/json");
        });

        get("/servers", (req, res) -> {
            return this.manager.GetServer();
        });

        post("/servers", (req, res) -> {
            return this.manager.AddServer(req.body());
        });

        put("/servers", (req, res) -> {
            return this.manager.AddServer(req.body());
        });

        delete("/servers/:name", (req, res) -> {
            this.manager.DeleteServer(req.params("name"));

            res.status(204);
            return "";
        });
    }
}