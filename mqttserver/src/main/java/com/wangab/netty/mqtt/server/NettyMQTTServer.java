package com.wangab.netty.mqtt.server;

import io.moquette.server.Server;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

/**
 * NettyMQTTServer
 *
 * @author Anbang Wang
 * @date 2017/5/3
 */
public class NettyMQTTServer {
    public static void main(String[] args) throws IOException, URISyntaxException {
        System.out.println("Server started, version 0.9 " + NettyMQTTServer.class.getClass().getResource("/").getPath() + "moquette.conf");
        final Server server = new Server();
        server.startServer(new File(NettyMQTTServer.class.getClass().getResource("/").getPath() + "moquette.conf"));
//        server.internalPublish();
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                server.stopServer();
            }
        });
    }
}
