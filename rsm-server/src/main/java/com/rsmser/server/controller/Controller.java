package com.rsmser.server.controller;

import com.rsmser.server.serverproxy.ServiceProxy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by zhenggm on 2017/7/24.
 */

@Path("/api")
public class Controller {
    private static final Logger logger = LogManager.getLogger(Controller.class);
    private ServiceProxy serviceProxy = new ServiceProxy();

    @GET
    @Produces("application/json")
    @Path("/server/list")
    public Response get() {
        String result = "get-server-list";
        String res = serviceProxy.requestRPC(result, null);
        logger.info(res);
        return Response.status(200).entity(res).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/task/list")
    public Response getTaskList() {
        String result = "get-task-list";
        String res = serviceProxy.requestRPC(result, null);
        logger.info(res);
        return Response.status(200).entity(res).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/tasklog/list")
    public Response getTasklogList(@QueryParam("page") String page,
                                   @QueryParam("server_id") String server_id,
                                   @QueryParam("task_start_time") String task_start_time,
                                   @QueryParam("task_end_time") String task_end_time) throws IOException {
        logger.info("getTasklogList start ===>");
        String result = "get-tasklog-list";
        Map map = new HashMap();
        map.put("page", page);
        if (server_id != null && !server_id.equals("")) {
            map.put("server_id", server_id);
        }
        if (null != task_start_time && !task_start_time.equals("")) {
            map.put("task_start_time", task_start_time);
            map.put("task_end_time", task_end_time);
        }
        String res = serviceProxy.requestRPC(result, map);
        logger.info(res);
        return Response.status(200).entity(res).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/tasklog/total")
    public Response getTasklogTotal(@QueryParam("server_id") String server_id,
                                    @QueryParam("task_start_time") String task_start_time,
                                    @QueryParam("task_end_time") String task_end_time) {
        Map map = new HashMap();
        if (null != server_id && !server_id.equals("")) {
            map.put("server_id", server_id);
        }
        if (null != task_start_time && !task_start_time.equals("")) {
            map.put("task_start_time", task_start_time);
            map.put("task_end_time", task_end_time);
        }
        String result = "get-tasklog-total";
        String res = serviceProxy.requestRPC(result, map);
        logger.info(res);
        return Response.status(200).entity(res).build();
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/timelark/server")
    public Response getTimelarkServer(@QueryParam("page") String page) throws IOException {
        logger.info("getTimelarkServer start ===>");
        String result = "get-timelark-server-list";
        Map map = new HashMap();
        map.put("page", page);
        String res = serviceProxy.requestRPC(result, map);
        logger.info(res);
        return Response.status(200).entity(res).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/timelark/task")
    public Response getTimelarkTask(@QueryParam("page") String page) throws IOException {
        logger.info("getTimelarkTask start ===>");
        String result = "get-timelark-task-list";
        Map map = new HashMap();
        map.put("page", page);
        String res = serviceProxy.requestRPC(result, map);
        logger.info(res);
        return Response.status(200).entity(res).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/timelark/tasklog")
    public Response getTimelarkTaskLog(@QueryParam("page") String page) throws IOException {
        logger.info("getTimelarkTaskLog start ===>");
        String result = "get-timelark-tasklog-list";
        Map map = new HashMap();
        map.put("page", page);
        String res = serviceProxy.requestRPC(result, map);
        logger.info(res);
        return Response.status(200).entity(res).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/timelark/tasklog/total")
    public Response getTimelogTotal() {
        String result = "get-timelark-tasklog-total";
        String res = serviceProxy.requestRPC(result, null);
        logger.info(res);
        return Response.status(200).entity(res).build();
    }

    // search server by id
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getServerById")
    public Response getServerById(@QueryParam("serverid") String serverid) {
        logger.info("getServerById start ===>");
        String result = "get-server-id";
        Map map = new HashMap();
        map.put("serverid", serverid);
        String res = serviceProxy.requestRPC(result, map);
        logger.info(res);
        return Response.status(200).entity(res).build();
    }
}
