package com.rsmser.server.nettyClient;


import com.rsmser.common.socket.SendMsgFuture;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * Created by dell on 2017/7/24.
 */
public class SimpleClientHandler extends SimpleChannelInboundHandler<ByteBuf> {

    private static final Logger logger = LogManager.getLogger(SimpleClientHandler.class);

    private SendMsgFuture future;

    public SimpleClientHandler(SendMsgFuture future) {
        this.future = future;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof FullHttpResponse) {
            logger.info("start read response");
            FullHttpResponse response = (FullHttpResponse) msg;
            ByteBuf buf = response.content();
            byte[] req = new byte[buf.readableBytes()];
            buf.readBytes(req);
            future.success(new String(req, "UTF-8"));
        }else{
            super.channelRead(ctx, msg);
        }
        ctx.flush();
        ctx.close();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

}
