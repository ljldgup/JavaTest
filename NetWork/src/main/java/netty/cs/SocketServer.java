package netty.cs;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
public class SocketServer {

		public static void main(String[] args) {
			EventLoopGroup bossGroup = new NioEventLoopGroup();
			EventLoopGroup workGroup = new NioEventLoopGroup();

			try {
				ServerBootstrap serverBootstrap = new ServerBootstrap();
				serverBootstrap.group(bossGroup,workGroup).channel(NioServerSocketChannel.class).
						childHandler(new SocketServerInitializer());
				ChannelFuture channelFuture = serverBootstrap.bind(8899).sync();
				channelFuture.channel().closeFuture().sync();
			} catch (Exception e){
				e.printStackTrace();
			} finally {
				bossGroup.shutdownGracefully();
				workGroup.shutdownGracefully();
			}
		}


}
