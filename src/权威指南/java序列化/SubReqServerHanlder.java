package 权威指南.java序列化;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;


public class SubReqServerHanlder extends ChannelHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        SubscibeReq sr = (SubscibeReq) msg;
        if("lilinfeng".equalsIgnoreCase(sr.getUserName())){
            System.out.println("server accept clint msg: " + sr.toString());
            ctx.writeAndFlush(resp(sr));
        }
    }

    private SubscribeResp resp(SubscibeReq sr){

        SubscribeResp result = new SubscribeResp();
        result.setSubReqID(sr.getSubReqID());
        result.setRespCode(0);
        result.setDesc("good good  good ");
        return result;
    }
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
