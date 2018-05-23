package qhw.wechat.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "xml")
@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
public class TextMsgEntity extends BaseMsgEntity{
	private String Content;
	private String MsgId;
	
	public String getContent() {
		return Content;
	}
	@XmlElement(name = "Content")
	public void setContent(String content) {
		Content = content;
	}
	public String getMsgId() {
		return MsgId;
	}
	@XmlElement(name = "MsgId")
	public void setMsgId(String msgId) {
		MsgId = msgId;
	}
}
