package 权威指南.java序列化;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelHandlerInvoker;
import io.netty.util.concurrent.EventExecutorGroup;

public class SubReqClientHandler extends ChannelHandlerAdapter {

    public SubReqClientHandler() {
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        for (int i = 0; i < 10; i++) {
            ctx.write(subReq(i));
        }
        ctx.flush();

    }

    private SubscibeReq subReq(int i){
        SubscibeReq sr = new SubscibeReq();
        sr.setAddress("江宁织造");
        sr.setProductNumber("138585854587");
        sr.setProductName("netty权威指南");
        sr.setSubReqID(i);
        sr.setUserName("lilinfeng");

        return sr;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("receive response: >>> " + msg );
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
