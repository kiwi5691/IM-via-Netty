package com.IM.netty.enums;

/**
 * 
 * @Description: 消息类型
 */
public enum MsgTypeEnum {

	text(0, "text"),
	images(1, "images"),
	audience(2, "audience");

	public final Integer type;
	public final String content;

	MsgTypeEnum(Integer type, String content){
		this.type = type;
		this.content = content;
	}
	
	public String getType() {
		return content;
	}
}
