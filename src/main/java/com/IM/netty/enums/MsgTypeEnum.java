package com.IM.netty.enums;

/**
 * 
 * @Description: 消息类型
 */
public enum MsgTypeEnum {

	text(0, "文字"),
	image(1, "图片"),
	audience(2, "音频");

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
