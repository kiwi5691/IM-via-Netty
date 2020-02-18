package com.IM.netty.netty.handler;

import com.IM.netty.entity.UserMsg;
import com.IM.netty.enums.MsgActionEnum;
import com.IM.netty.enums.MsgSignFlagEnum;
import com.IM.netty.model.dto.ChatMsg;
import com.IM.netty.model.dto.DataContent;
import com.IM.netty.model.dto.WeChatMsg;
import com.IM.netty.service.UserMsgService;
import com.IM.netty.utils.JsonUtils;
import com.IM.netty.utils.SpringUtil;
import com.IM.netty.utils.TypeChecksUtils;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @Description: 处理消息的handler
 */
@Slf4j
public class ChatHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

	public static ChannelGroup users =
			new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

	// 获取客户端传输过来的消息
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) 
			throws Exception {

		String content = msg.text();
		
		Channel currentChannel = ctx.channel();

		// 1. 获取客户端发来的消息
		DataContent dataContent = JsonUtils.jsonToPojo(content, DataContent.class);
		Integer action = dataContent.getAction();
		log.info("dataContent:"+dataContent.toString()+"channel:"+currentChannel.toString());
		// 判断消息类型，根据不同的类型来处理不同的业务

		if (action == MsgActionEnum.CONNECT.type) {
			//初始化channel，把用的channel和userid关联起来
			String senderId = dataContent.getChatMsg().getSenderId();
			UserChannelRel.put(senderId, currentChannel);
			
			// 测试ChannelGroup
//			for (Channel c : users) {
//				System.out.println(c.id().asLongText());
//			}
			UserChannelRel.output();
		} else if (action == MsgActionEnum.CHAT.type) {
			//  聊天类型
			// 这里的测试页面不对签收和未签收进行判断
			ChatMsg chatMsg = dataContent.getChatMsg();
			String msgText = chatMsg.getMsg();
			String receiverId = chatMsg.getReceiverId();

			UserMsgService userMsgService = (UserMsgService) SpringUtil.getBean("userMsgService");
			// 保存消息到db，标记->未签收
			Long msgId = userMsgService.insert(chatMsg);
			chatMsg.setMsgId(String.valueOf(msgId));

			/*小程序初始页面 ，这里关闭了缓存
			UserInfoDTOLocalCache.setNewMessage(Integer.parseInt(receiverId),Integer.parseInt(chatMsg.getSenderId()),msgText);
			//这里其实对于channel中没有userId之分
			UserInfoDTOLocalCache.setNewMessage(Integer.parseInt(chatMsg.getSenderId()),Integer.parseInt(receiverId),msgText);

			WeChatMsg weChatMsg = new WeChatMsg(dataContent, MsgSignFlagEnum.signed.type);
			UserMsgLocalCache.add(Integer.parseInt(receiverId),Integer.parseInt(chatMsg.getSenderId()),weChatMsg);
			UserMsgLocalCache.add(Integer.parseInt(chatMsg.getSenderId()),Integer.parseInt(receiverId),weChatMsg);
			*/

			DataContent dataContentMsg = new DataContent();
			dataContentMsg.setChatMsg(chatMsg);
			dataContentMsg.setExtand(TypeChecksUtils.returnType(msgText));
			UserChannelRel.output();
			// 发送消息

			// 从全局用户Channel关系中获取接受方的channel
			Channel receiverChannel = UserChannelRel.get(receiverId);
			if (receiverChannel == null) {
				//  channel为空代表用户离线，推送消息
			} else {
				// 当receiverChannel不为空的时候，从ChannelGroup去查找对应的channel是否存在
				Channel findChannel = users.find(receiverChannel.id());
				if (findChannel != null) {
					// 用户在线
					receiverChannel.writeAndFlush(
							new TextWebSocketFrame(
									JsonUtils.objectToJson(dataContentMsg)));
				} else {
					// 用户离线  推送消息
				}
			}
			
		} else if (action == MsgActionEnum.SIGNED.type) {

			UserMsgService userMsgService = (UserMsgService) SpringUtil.getBean("userMsgService");
			// 扩展字段在signed类型的消息中，代表需要去签收的消息id，逗号间隔
			String msgIdsStr = dataContent.getExtand();
			String msgIds[] = msgIdsStr.split(",");
			
			List<String> msgIdList = new ArrayList<>();
			for (String mid : msgIds) {
				if (StringUtils.isNotBlank(mid)) {
					msgIdList.add(mid);
				}
			}
			if (msgIdList != null && !msgIdList.isEmpty() && msgIdList.size() > 0) {


				// 批量签收
				 userMsgService.updateMsgSigned(msgIdList);
				/*关闭缓存
				 UserMsgLocalCache.updateSign(msgIdList);
 				*/
			}


		} else if (action == MsgActionEnum.KEEPALIVE.type) {
			//心跳类型的消息
			System.out.println("收到来自channel为[" + currentChannel + "]的心跳包...");
		}
	}
	
	/**
	 * 当客户端连接服务端之后（打开连接）
	 * 获取客户端的channle，并且放到ChannelGroup中去进行管理
	 */
	@Override
	public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
		users.add(ctx.channel());
	}

	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
		
		String channelId = ctx.channel().id().asShortText();
		System.out.println("客户端被移除，channelId为：" + channelId);
		// 当触发handlerRemoved，ChannelGroup会自动移除对应客户端的channel
		users.remove(ctx.channel());
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		// 发生异常之后关闭连接（关闭channel），随后从ChannelGroup中移除
		ctx.channel().close();
		users.remove(ctx.channel());
	}
}
