package com.rsmser.server.nettyClient;

import com.rsmser.common.socket.SendMsgFuture;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.http.*;
import io.netty.handler.stream.ChunkedWriteHandler;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class NettyClient {

    public SendMsgFuture connect(int port, String host, String k, Map map) {

        EventLoopGroup group = new NioEventLoopGroup();
        SendMsgFuture future = new SendMsgFuture();
        try {

            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            // pipeline.addLast(new IdleStateHandler(4,5,6, TimeUnit.SECONDS));

                            // 客户端，对请求编码
                            ch.pipeline().addLast("http-encoder",
                                    new HttpRequestEncoder());
                            // 客户端，对响应解码
                            ch.pipeline().addLast("http-decoder",
                                    new HttpResponseDecoder());
                            // 聚合器，把多个消息转换为一个单一的FullHttpRequest或是FullHttpResponse
                            ch.pipeline().addLast("http-aggregator",
                                    new HttpObjectAggregator(1024*1024*1024));
                            // 块写入处理器
                            ch.pipeline().addLast("http-chunked",
                                    new ChunkedWriteHandler());
                            ch.pipeline().addLast(new SimpleClientHandler(future));
                        }
                    });

            //发起异步链接操作
            ChannelFuture channelFuture = bootstrap.connect(host, port).sync();

            DefaultFullHttpRequest request = setHttpMsg(k, "GET", map);
            channelFuture.channel().write(request);
            channelFuture.channel().flush();
            future.sync(60, TimeUnit.SECONDS);
            channelFuture.channel().closeFuture().sync();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            //关闭，释放线程资源
            group.shutdownGracefully();
        }
        return future;
    }

    public SendMsgFuture startNode(String k, Map map) {
        return new NettyClient().connect(8989, "127.0.0.1", k, map);
    }

    public SendMsgFuture startNode2(String k, Map map) {
        return new NettyClient().connect(8989, "192.168.1.64", k, map);
    }


    public DefaultFullHttpRequest setHttpMsg(String msg, String method, Map map) {
        URI uri = null;
        DefaultFullHttpRequest request = null;
        // 无参数情况下
        if (map == null) {
            try {
                uri = new URI("http://192.168.1.64:8989");
                request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.valueOf(method),
                        uri.toASCIIString(), Unpooled.wrappedBuffer(msg.getBytes("UTF-8")));

            } catch (Exception e) {
                e.printStackTrace();
            }
            // 构建http请求
            request.headers().set(HttpHeaders.Names.CONNECTION, HttpHeaders.Values.KEEP_ALIVE);
            request.headers().set(HttpHeaders.Names.CONTENT_LENGTH, request.content().readableBytes());
        } else {
            // 带参数情况下
            Iterator it = map.entrySet().iterator();
            try {
                StringBuffer sb = new StringBuffer();
                sb.append("http://127.0.0.1:8989?");
                while (it.hasNext()) {
                    Map.Entry entry = (Map.Entry) it.next();
                    sb.append(entry.getKey().toString()).append("=").append(entry.getValue().toString()).append("&");
                }
                sb.deleteCharAt(sb.length() - 1);
                System.out.println(sb.toString());
                uri = new URI(sb.toString());
                request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.valueOf(method),
                        uri.toASCIIString(), Unpooled.wrappedBuffer(msg.getBytes("UTF-8")));
            } catch (Exception e) {
                e.printStackTrace();
            }
            // 构建http请求
            request.headers().set(HttpHeaders.Names.CONNECTION, HttpHeaders.Values.KEEP_ALIVE);
            request.headers().set(HttpHeaders.Names.CONTENT_LENGTH, request.content().readableBytes());
        }
        return request;
    }

}
