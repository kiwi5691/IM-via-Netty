package com.IM.netty.config;

import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

public class NettyConfig {
    // 存储所有连接的 channel
//    public static ChannelGroup group = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    // host name 和监听的端口号，需要配置到配置文件中
    public static String WS_HOST = "127.0.0.1";
    public static int WS_PORT = 8088;
}
