package com.rsmnode.server.service;

import com.rsmnode.server.dao.ServiceDao;
import com.rsmnode.server.dao.ServiceDaoImpl;
import com.rsmser.common.socket.Utils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by dell on 2017/7/25.
 */
public class Service {

    private static final Logger logger = LogManager.getLogger(Service.class);
    private ServiceDao serviceDao = new ServiceDaoImpl();

    public String getServerList() {
        logger.info("getAllService service start");
        List<Map<String, Object>> mapList = serviceDao.getServerListDao();
        String json = Utils.transToJson(mapList);
        return json;
    }

    public String getTasklogList(Map map) throws IOException {
        logger.info("getTasklogList service start");
        List<Map<String, Object>> mapList = serviceDao.getTasklogListDao(map);
        return Utils.transToJson(mapList);
    }

    public String getTasklogTotal(Map map) {

        List<Map<String, Object>> mapList = serviceDao.getTasklogTotalDao(map);
        return Utils.transToJson(mapList);
    }

    public String getTaskList() {
        logger.info("getTaskList service start");
        List<Map<String, Object>> mapList = serviceDao.getTaskListDao();
        String json = Utils.transToJson(mapList);
        return json;
    }

    public String getTimelarkServer(Map map) throws IOException {
        logger.info("getTimelarkServer service start");
        Long page = Long.valueOf(map.get("page").toString());
        List<Map<String, Object>> mapList = serviceDao.getTimelarkServerDao(page);
        return Utils.transToJson(mapList);
    }

    public String getTimelarkTask(Map map) throws IOException {
        logger.info("getTimelarkTask service start");
        Long page = Long.valueOf(map.get("page").toString());
        List<Map<String, Object>> mapList = serviceDao.getTimelarkTaskDao(page);
        return Utils.transToJson(mapList);
    }


    public String getTimelarkTaskLog(Map map) throws IOException {
        logger.info("getTimelarkTaskLog service start");
        Long page = Long.valueOf(map.get("page").toString());
        List<Map<String, Object>> mapList = serviceDao.getTimelarkTaskLogDao(page);
        return Utils.transToJson(mapList);
    }

    public String getTimelogTotal() {
        logger.info("getTimelogTotal service start");
        List<Map<String, Object>> mapList = serviceDao.getTimelogTotalDao();
        return Utils.transToJson(mapList);
    }

    public String getServerById(Map map){
        logger.info("getServerById service start");
        String id = (String) map.get("serverid");
        List<Map<String, Object>> mapList = serviceDao.getServerByIdDao(id);
        return Utils.transToJson(mapList);
    }
}
