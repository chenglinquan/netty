package 权威指南.netty入门解码器;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

public class TimeServer {


    public void bind( int port){

        //NioEventLoopGroup  是一个线程组 包含了一组NIO线程。专门用于网络事件的处理
        EventLoopGroup boosGroup = new NioEventLoopGroup();// 用于服务端接受客户端连接
        EventLoopGroup workGroup = new NioEventLoopGroup();// 用于进行socketchannel的网络读写


        try {
            ServerBootstrap sb = new ServerBootstrap();// 用于启动NIO服务端的辅助启动类
            sb.group(boosGroup,workGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG,1024)
                    .childHandler(new ChildChannelHanlder());

            // 绑定端口 同步等待成功
            ChannelFuture cf = sb.bind(port).sync();    // future ： 用于异步操作的通知回调

            // 等待服务端监听端口关闭
            cf.channel().closeFuture().sync();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            boosGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }

    /**
     * 主要用于处理网络I/O事件，例如记录日志、对消息进行编解码
     */
    private class ChildChannelHanlder extends ChannelInitializer<SocketChannel> {
        @Override
        protected void initChannel(SocketChannel socketChannel) throws Exception {

            socketChannel.pipeline().addLast(new LineBasedFrameDecoder(1024));
            socketChannel.pipeline().addLast(new StringDecoder());
            socketChannel.pipeline().addLast(new TimeServerHandler());
        }
    }

    public static void main(String[] args) {
        new TimeServer().bind(858);
    }
}
