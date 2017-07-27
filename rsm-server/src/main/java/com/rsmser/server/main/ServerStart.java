package com.rsmser.server.main;

import com.rsmser.server.controller.Controller;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jboss.resteasy.spi.Registry;


public class ServerStart {

    private static final Logger logger = LogManager.getLogger(ServerStart.class);

    // 启动rsm-server服务
    public static void main(String[] args) throws Exception {
        Registry registry = NettyContainer.start().getRegistry();
        registry.addPerRequestResource(Controller.class);
        logger.info("******RSM-SERVER IS STARTED******");
    }
}
