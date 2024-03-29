package 权威指南.netty入门解码器;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.util.logging.Logger;

public class TimeClientHandler extends ChannelHandlerAdapter {

    private static final Logger logger = Logger.getLogger(TimeClientHandler.class.getName());

    private byte[] req;
    private int counter;

    public TimeClientHandler() {
         req = ("QUERY TIME ORDER" + System.getProperty("line.separator")) .getBytes();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        ByteBuf buf = null;

        for (int i = 0; i < 100; i++) {
            buf = Unpooled.buffer(req.length);
            buf.writeBytes(req);
            ctx.writeAndFlush(buf);
        }
    }


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

       /* ByteBuf buf = (ByteBuf) msg;
        byte[] req = new byte[buf.readableBytes()];
        buf.readBytes(req);
        String body = new String(req,"UTF-8");*/
        String body = (String) msg;
        System.out.println("now is : " +body + " ; the counter is :" + ++counter);

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.info(cause.toString());
        ctx.close();
    }
}
