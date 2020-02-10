package com.IM.netty.netty;

import com.IM.netty.config.NettyConfig;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;

@Component
public class NettyServer {

	private static class SingletionWSServer {
		static final NettyServer instance = new NettyServer();
	}

	public static NettyServer getInstance() {
		return SingletionWSServer.instance;
	}

	private EventLoopGroup bossGroup;
	private EventLoopGroup workGroup;
	private ServerBootstrap server;
	private ChannelFuture future;
	private InetSocketAddress address;

	public NettyServer() {
		bossGroup = new NioEventLoopGroup();
		workGroup = new NioEventLoopGroup();
		server = new ServerBootstrap();
		server.group(bossGroup, workGroup)
			.channel(NioServerSocketChannel.class)
			.childHandler(new ServerInitializer());
	}
	
	public void start() {
		address = new InetSocketAddress(NettyConfig.WS_HOST, NettyConfig.WS_PORT);

		this.future = server.bind(address);
		System.err.println("netty websocket server 启动完毕...");
	}
}
