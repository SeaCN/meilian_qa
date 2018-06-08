package qhw.wechat.entity.meten;

public class FeedbackBean extends BaseBean {
	private Integer userid;
	private Integer suggestionid;
	private String content;
	public Integer getUserid() {
		return userid;
	}
	public void setUserid(Integer userid) {
		this.userid = userid;
	}
	public Integer getSuggestionid() {
		return suggestionid;
	}
	public void setSuggestionid(Integer suggestionid) {
		this.suggestionid = suggestionid;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
}
