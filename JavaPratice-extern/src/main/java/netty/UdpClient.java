package netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.net.InetSocketAddress;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
@Slf4j
public class UdpClient {

    private Bootstrap bootstrap;

    private NioEventLoopGroup group;

    private Channel channel;

    private ExecutorService executorService = Executors.newFixedThreadPool(1);


    private UdpClient() {
        executorService.execute(() -> {
                    try {
                        group = new NioEventLoopGroup();
                        bootstrap = new Bootstrap();
                        bootstrap.group(group)
                                .channel(NioDatagramChannel.class)
                                .handler(new ChannelInitializer<NioDatagramChannel>() {
                                    @Override
                                    protected void initChannel(NioDatagramChannel ch) {
                                        ChannelPipeline pipeline = ch.pipeline();
                                        pipeline.addLast(new PackageHandler());
                                    }
                                });

                        channel = bootstrap.bind(0).sync().channel();
                        ByteBuf data = Pooled.buffer();
                        data.writeBytes(ByteBufUtil.decodeHexDump("686167303030313d00000016"));
                        channel.writeAndFlush(new DatagramPacket(data, new InetSocketAddress("127.0.0.1",61458))).await();
                        System.out.println("UdpClient start success");
                        channel.closeFuture().await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
        );
    }


    @Slf4j
    public static class PackageHandler extends SimpleChannelInboundHandler<DatagramPacket> {

        //接受服务端发送的内容
        @Override
        protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket packet) {
            System.out.println(ByteBufUtil.hexDump(packet.content()));
        }

    }

    public static void main(String[] args) {
        UdpClient udpClient = new UdpClient();
    }


}
