package 权威指南.netty入门解码器;

import com.sun.org.apache.bcel.internal.classfile.ConstantUtf8;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.nio.ByteBuffer;
import java.util.Date;

public class TimeServerHandler extends ChannelHandlerAdapter {

    private int counter;
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        /*ByteBuf buf = (ByteBuf) msg;
        byte[] req = new byte[buf.readableBytes()];
        buf.readBytes(req);*/
        //String body = new String(req, "utf-8");
        /*String body = new String(req, "utf-8")
                .substring(0,req.length - System.getProperty("line.separator").length());*/

        String body = (String) msg;

        System.out.println("the time server receive order :" + body + " ; the counter is :"  + ++counter);
        String currentTime = "QUERY TIME ORDER".equalsIgnoreCase(body)?
                        new Date(System.currentTimeMillis()).toString():"BAD ORDER";
        currentTime = currentTime + System.getProperty("line.separator");
        ByteBuf resp = Unpooled.copiedBuffer(currentTime.getBytes());
        ctx.write(resp);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
