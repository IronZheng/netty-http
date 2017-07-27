package com.rsmnode.server.command;

import com.rsmnode.server.service.Service;

import java.io.IOException;
import java.util.Map;

/**
 * 指令接收类，根据指令执行相应方法。
 */
public class Command {

    private Service service = new Service();

    public String serverList(String command, Map map) throws IOException {
        String result = new String();

        if (command.isEmpty() || command == "") {
            return null;
        }
        if (map.size() == 0 || map == null){
            switch (command) {

                case "get-server-list":
                    result = service.getServerList();
                    break;

                case "get-task-list":
                    result = service.getTaskList();
                    break;
                case  "get-timelark-tasklog-total":
                    result = service.getTimelogTotal();
                    break;
                case "get-tasklog-total":
                    result = service.getTasklogTotal(map);
                    break;
            }
        }else{
            switch (command) {
                case "get-tasklog-list":
                    result = service.getTasklogList(map);
                    break;
                case "get-tasklog-total":
                    result = service.getTasklogTotal(map);
                    break;
                case "get-timelark-server-list":
                    result = service.getTimelarkServer(map);
                    break;
                case "get-timelark-task-list":
                    result = service.getTimelarkTask(map);
                    break;
                case "get-timelark-tasklog-list":
                    result = service.getTimelarkTaskLog(map);
                    break;
                case "get-server-id":
                    result = service.getServerById(map);
                    break;
            }
        }

        return result;
    }
}
