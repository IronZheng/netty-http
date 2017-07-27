package com.rsmser.server.serverproxy;

import com.rsmser.common.socket.EncryptUtil;
import com.rsmser.common.socket.Status;
import com.rsmser.server.nettyClient.NettyClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

/**
 * Created by zhenggm on 2017/7/25.
 */
public class ServiceProxy {

    private final static Logger logger = LogManager.getLogger(ServiceProxy.class);
    private NettyClient nettyClient = new NettyClient();

    public String requestRPC(String message, Map<String, String> map) {
        message = EncryptUtil.encrypt(message, Status.KEY);
        String result = nettyClient.startNode(message, map).toString();
        logger.info("receive response <=======" + result);
        result = EncryptUtil.decrypt(result, Status.KEY);
        return result;
    }

}
