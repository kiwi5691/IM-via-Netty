package com.IM.netty;

import com.IM.netty.netty.NettyServer;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class NettyBooter implements ApplicationListener<ContextRefreshedEvent> {

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if(event.getApplicationContext().getParent()==null) {
			try {
				NettyServer.getInstance().start();
			} catch (Exception e) {
				//todo 这里需要再次判断如果报错是否会直接shutdown
				e.printStackTrace();
				NettyServer.getInstance().shutdown();
			}
		}

	}

}
