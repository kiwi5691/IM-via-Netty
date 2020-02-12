package com.IM.netty.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatMsg implements Serializable {

	private static final long serialVersionUID = 3611169682695799175L;
	
	private String senderId;		// 发送者的用户id
	private String receiverId;		// 接受者的用户id
	private String msg;				// 聊天内容
	private String msgId;			// 用于消息的签收


}
