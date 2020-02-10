package com.IM.netty;

import com.IM.netty.config.NettyConfig;
import com.IM.netty.netty.ServerBootStrap;
import io.netty.channel.ChannelFuture;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.net.InetSocketAddress;

@Slf4j
@SpringBootApplication
public class IMApplication implements CommandLineRunner {

    @Autowired
    private ServerBootStrap ws;
    public static void main(String[] args) {
        SpringApplication.run(IMApplication.class, args);
    }
    @Override
    public void run(String... args) throws Exception {
        log.info("Netty's ws server is listen: " + NettyConfig.WS_PORT);
        InetSocketAddress address = new InetSocketAddress(NettyConfig.WS_HOST, NettyConfig.WS_PORT);
        ChannelFuture future = ws.start(address);

        Runtime.getRuntime().addShutdownHook(new Thread(){
            @Override
            public void run() {
                ws.destroy();
            }
        });

        future.channel().closeFuture().syncUninterruptibly();
    }
}
