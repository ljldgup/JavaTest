package netty.http;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class NettyServer {

	public static void main(String[] args) throws Exception {

		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workGroup = new NioEventLoopGroup();
		try {
			ServerBootstrap serverBootstrap = new ServerBootstrap();
//			原来bossGroup就是parentGroup，是负责处理TCP/IP连接的，
//			而workerGroup就是childGroup，是负责处理Channel（通道）的I/O事件。
			serverBootstrap.group(bossGroup, workGroup).channel(NioServerSocketChannel.class)
					.childHandler(new TestServerInitializer());
			//ChannelFuture的作用是用来保存Channel异步操作的结果。
//			如果用户操作调用了sync或者await方法，会在对应的future对象上阻塞用户线程，例如future.channel().closeFuture().sync()
//			bootstrap.connect().sync(); //直到连接返回，才会继续后面的执行，否则阻塞当前线程
//			future.channel().closeFuture().sync() //直到channel关闭，才会继续后面的执行，否则阻塞当前线程
			//直接访问localhost:8899
			ChannelFuture channelFuture = serverBootstrap.bind(8899).sync();
			channelFuture.channel().closeFuture().sync();
		} finally {
			bossGroup.shutdownGracefully();
			workGroup.shutdownGracefully();
		}
	}


}
