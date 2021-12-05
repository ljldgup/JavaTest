package netty.cs;

import java.util.Date;
import java.util.Random;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class SocketClientHandler extends SimpleChannelInboundHandler<String> {

	Random random = new Random();

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, String s) throws Exception {
		System.out.println(ctx.channel().remoteAddress());
		System.out.println("client output" + s);
		ctx.writeAndFlush("from client:" + new Date());

	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("来自客户端的问候");
		ctx.writeAndFlush("from client:" + new Date());
		if (random.nextInt() % 9 == 0) throw new RuntimeException("模拟抛错关闭");
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}
}

