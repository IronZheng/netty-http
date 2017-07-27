package com.rsmnode.server.dao;


import com.rsmnode.server.jdbc.JdbcUtil;
import com.rsmser.common.socket.DateUtil;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by dell on 2017/7/17.
 */
public class ServiceDaoImpl implements ServiceDao {

    private static JdbcUtil jdbcUtil = new JdbcUtil();


    @Override
    public List<Map<String, Object>> getServerListDao() {
        String sql = "select * from dbbatch_server";
        return execSqlQuery(sql);
    }

    @Override
    public List<Map<String, Object>> getTaskListDao() {
        String sql = "select * from dbbatch_task ";
        return execSqlQuery(sql);
    }

    @Override
    public List<Map<String, Object>> getTasklogListDao(Map map) throws IOException {
        Long page = Long.valueOf((String) map.get("page"));
        page = (page - 1) * 20;
        String sql = null;
        if (map.containsKey("server_id")) {
            if (map.containsKey("task_start_time")) {
                String server_id = (String) map.get("server_id");
                String sDate = DateUtil.DateStrToStr((String) map.get("task_start_time"));
                String eDate = DateUtil.DateStrToStr((String) map.get("task_end_time"));
                sql = "select *from dbbatch_task_log where server_id like '%" + server_id + "%' and task_start_time >= '" + sDate
                        + "' and task_end_time <= '" + eDate + "' limit " + page + ",20";
            } else {
                String server_id = (String) map.get("server_id");
                sql = "select * from dbbatch_task_log where server_id like '%" + server_id + "%' limit " + page + ",20";
            }
        } else {
            if (!map.containsKey("task_start_time")) {
                sql = "select *from dbbatch_task_log limit " + page + ",20";
            } else {
                String sDate = DateUtil.DateStrToStr((String) map.get("task_start_time"));
                String eDate = DateUtil.DateStrToStr((String) map.get("task_end_time"));
                sql = "select *from dbbatch_task_log where task_start_time >= '" + sDate
                        + "' and task_end_time <= '" + eDate + "' limit " + page + ",20";
            }
        }
        return execSqlQuery(sql);
    }

    @Override
    public List<Map<String, Object>> getTasklogTotalDao(Map map) {
        String sql = null;
        if (map.containsKey("server_id")) {
            if (map.containsKey("task_start_time")) {
                String server_id = (String) map.get("server_id");
                String sDate = DateUtil.DateStrToStr((String) map.get("task_start_time"));
                String eDate = DateUtil.DateStrToStr((String) map.get("task_end_time"));
                sql = "select count(*) as total from dbbatch_task_log where server_id like '%" + server_id + "%' and task_start_time >= '" + sDate
                        + "' and task_end_time <= '" + eDate + "'";
            } else {
                String server_id = (String) map.get("server_id");
                sql = "select count(*) as total from dbbatch_task_log where server_id like '%" + server_id + "%'";
            }
        } else {
            if (!map.containsKey("task_start_time")) {
                sql = "select count(*) as total from dbbatch_task_log";
            } else {
                String sDate = DateUtil.DateStrToStr((String) map.get("task_start_time"));
                String eDate = DateUtil.DateStrToStr((String) map.get("task_end_time"));
                sql = "select count(*) as total from dbbatch_task_log where task_start_time >= '" + sDate + "' and task_end_time <= '" + eDate + "'";
            }
        }

        return execSqlQuery(sql);
    }

    @Override
    public List<Map<String, Object>> getTimelarkServerDao(Long page) throws IOException {
        page = (page - 1) * 20;
        String sql = "select *from timelark_server limit " + page + ",20";
        //创建填充参数的list
        return execSqlQuery(sql);
    }

    @Override
    public List<Map<String, Object>> getTimelarkTaskDao(Long page) throws IOException {
        page = (page - 1) * 20;
        String sql = "select *from timelark_task limit " + page + ",20";
        return execSqlQuery(sql);
    }

    @Override
    public List<Map<String, Object>> getTimelarkTaskLogDao(Long page) throws IOException {
        page = (page - 1) * 20;
        String sql = "select *from timelark_task_log limit " + page + ",20";
        return execSqlQuery(sql);
    }

    @Override
    public List<Map<String, Object>> getTimelogTotalDao() {
        String sql = "select count(*) as total from timelark_task_log";
        return execSqlQuery(sql);
    }

    @Override
    public List<Map<String, Object>> getServerByIdDao(String id) {
        String sql = "select * from dbbatch_server where server_id like '%" + id + "%'";
        return execSqlQuery(sql);
    }


    public List<Map<String, Object>> execSqlQuery(String sql) {
        //创建填充参数的list
        List<Object> paramList = new ArrayList<Object>();
        List<Map<String, Object>> mapList = new ArrayList<>();
        jdbcUtil.getConnection(); // 获取数据库链接
        try {
            mapList = jdbcUtil.findResult(
                    sql.toString(), paramList);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (jdbcUtil != null) {
                jdbcUtil.releaseConn(); // close
            }
        }
        return mapList;
    }
}
