package com.rsmnode.server.command;

import com.rsmser.common.socket.EncryptUtil;
import com.rsmser.common.socket.Status;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Map;

/**
 * Created by dell on 2017/7/26.
 */
public class EncryptMsg {

    private final Logger logger = LogManager.getLogger(EncryptMsg.class);
    private Command command = new Command();

    public String encryptMsg(String msg, Map map) throws IOException {
        synchronized (this) {
            msg = EncryptUtil.decrypt(msg, Status.KEY);
            logger.info("decode msg =>>>>" + msg);
            String result = command.serverList(msg, map);
            result = EncryptUtil.encrypt(result, Status.KEY);
            return result;
        }
    }
}
