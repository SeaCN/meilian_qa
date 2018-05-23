package qhw.wechat.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "xml")
@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
public class BaseMsgEntity {
	private String ToUserName;
	private String FromUserName;
	private Long CreateTime;
	private String MsgType;
	
	public String getToUserName() {
		return ToUserName;
	}
	@XmlElement(name = "ToUserName")
	public void setToUserName(String toUserName) {
		ToUserName = toUserName;
	}
	public String getFromUserName() {
		return FromUserName;
	}
	@XmlElement(name = "FromUserName")
	public void setFromUserName(String fromUserName) {
		FromUserName = fromUserName;
	}
	public Long getCreateTime() {
		return CreateTime;
	}
	@XmlElement(name = "CreateTime")
	public void setCreateTime(Long createTime) {
		CreateTime = createTime;
	}
	public String getMsgType() {
		return MsgType;
	}
	@XmlElement(name = "MsgType")
	public void setMsgType(String msgType) {
		MsgType = msgType;
	}
}
