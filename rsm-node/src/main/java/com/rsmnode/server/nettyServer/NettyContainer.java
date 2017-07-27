package com.rsmnode.server.nettyServer;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.stream.ChunkedWriteHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * netty server
 * Created by zhenggm on 2016/9/26 15:25.
 */
public class NettyContainer {

    private static final Logger logger = LogManager.getLogger(NettyContainer.class);
    private static final int port = 8989;

    public static void main(String[] args) {

        logger.info("NettyContainer Server is starting");
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup,workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .childHandler(new ChildChannelHandler());

            //绑定端口、同步等待
            ChannelFuture futrue = serverBootstrap.bind(port).sync();
            logger.info("NettyContainer Server is started");
            //等待服务监听端口关闭
            futrue.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally{
            //退出，释放线程等相关资源
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }


    }

    private static class ChildChannelHandler extends ChannelInitializer<SocketChannel> {
        @Override
        protected void initChannel(SocketChannel ch) throws Exception {
            // 服务端，对请求解码
            ch.pipeline().addLast("http-decoder",
                    new HttpRequestDecoder());
            // 聚合器，把多个消息转换为一个单一的FullHttpRequest或是FullHttpResponse
            ch.pipeline().addLast("http-aggregator",
                    new HttpObjectAggregator(1024*1024*1024));
            // 服务端，对响应编码
            ch.pipeline().addLast("http-encoder",
                    new HttpResponseEncoder());
            // 块写入处理器
            ch.pipeline().addLast("http-chunked",
                    new ChunkedWriteHandler());

            // 自定义服务端处理器
            ch.pipeline().addLast(new EchoServerHandler());
        }
    }
}
