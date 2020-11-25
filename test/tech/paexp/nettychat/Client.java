package tech.paexp.nettychat;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.ReferenceCountUtil;

public class Client {
    private Channel channel = null;
    public void connect() {
        EventLoopGroup workerGroup = new NioEventLoopGroup(1);
        Bootstrap b = new Bootstrap();
        try {
            b.group(workerGroup);
            b.channel(NioSocketChannel.class);
            b.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel socketChannel) {
                    channel = socketChannel;
                    socketChannel.pipeline().addLast(new MyHandler());
                }
            });

            ChannelFuture future = b.connect("localhost", 8888).sync();
            System.out.println("connected to server");

            future.channel().closeFuture().sync();
            System.out.println("go on");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            workerGroup.shutdownGracefully();
        }
    }

    public void send(String text) {
        channel.writeAndFlush(Unpooled.copiedBuffer(text.getBytes()));
    }

    public void closeConnection() {
        send("__byte__");
        channel.close();
    }

    static class MyHandler extends ChannelInboundHandlerAdapter {
        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) {
            ByteBuf buf = null;
            try {
                buf = (ByteBuf)msg;
                byte[] bytes = new byte[buf.readableBytes()];
                buf.getBytes(buf.readerIndex(),bytes);
                String str = new String(bytes);
                ClientFrame.INSTANCE.updateText(str);
                //System.out.println(str);
                //buf.refcnt拿到引用数
                //System.out.println(buf.refCnt());
            } finally {
                if (buf != null) {
                    ReferenceCountUtil.release(buf);
                }
            }
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
            cause.printStackTrace();
            ctx.close();
        }
    }
}
