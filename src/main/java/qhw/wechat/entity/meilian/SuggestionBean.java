package qhw.wechat.entity.meilian;

public class SuggestionBean extends BaseBean {
	private String title;
	private String content;
	private Integer userid;
	private Integer isFeedback;
	private String img;
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public Integer getIsFeedback() {
		return isFeedback;
	}
	public void setIsFeedback(Integer isFeedback) {
		this.isFeedback = isFeedback;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Integer getUserid() {
		return userid;
	}
	public void setUserid(Integer userid) {
		this.userid = userid;
	}
	
}
