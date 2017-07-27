package com.rsmnode.server.dao;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by dell on 2017/7/17.
 */
public interface ServiceDao {

    List<Map<String, Object>> getServerListDao();

    List<Map<String, Object>> getTaskListDao();

    List<Map<String, Object>> getTasklogListDao(Map page) throws IOException;

    List<Map<String, Object>> getTasklogTotalDao(Map map);

    List<Map<String, Object>> getTimelarkServerDao(Long page) throws IOException;

    List<Map<String, Object>> getTimelarkTaskDao(Long page) throws IOException;

    List<Map<String, Object>> getTimelarkTaskLogDao(Long page) throws IOException;

    List<Map<String, Object>> getTimelogTotalDao();

    List<Map<String, Object>> getServerByIdDao(String id);
}
