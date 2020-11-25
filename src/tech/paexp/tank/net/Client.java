package tech.paexp.tank.net;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.ReferenceCountUtil;
import tech.paexp.tank.TankFrame;

public class Client {
    public static final Client INSTANCE = new Client();

    private Client(){}

    private Channel channel = null;

    public static void main(String[] args) {
        Client c = new Client();
        c.connect();
    }

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
                    socketChannel.pipeline()
                            // 给管道加一些处理器
                            .addLast(new MsgEncoder())
                            .addLast(new MsgDecoder())
                            .addLast(new MyHandler());
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

    public void send(TankJoinMsg msg) {
        channel.writeAndFlush(msg);
    }

    public void closeConnection() {
        channel.close();
    }

    static class MyHandler extends SimpleChannelInboundHandler<TankJoinMsg> {
        @Override
        public void channelActive(ChannelHandlerContext ctx) {
            ctx.writeAndFlush(new TankJoinMsg(TankFrame.INSTANCE.getGameModel().getMyTank()));
        }

        @Override
        protected void channelRead0(ChannelHandlerContext channelHandlerContext, TankJoinMsg tankJoinMsg) throws Exception {
            System.out.println(tankJoinMsg);
            tankJoinMsg.handle();
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
            cause.printStackTrace();
            ctx.close();
        }
    }
}
