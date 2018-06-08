package qhw.wechat.entity.meten;

import java.util.Date;

public class UserBean extends BaseBean{
	private String openid;
	private String nickname;
	private String phone;
	private String wechat_account;
	private String wechat_nickname;
	private String wechat_sex;
	private String wechat_country;
	private String wechat_province;
	private String wechat_city;
	private String wechat_headimgurl;
	private String wechat_privilege;
	private String wechat_unionid;
	private Date updateTime;
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getWechat_city() {
		return wechat_city;
	}
	public void setWechat_city(String wechat_city) {
		this.wechat_city = wechat_city;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getWechat_account() {
		return wechat_account;
	}
	public void setWechat_account(String wechat_account) {
		this.wechat_account = wechat_account;
	}
	public String getWechat_nickname() {
		return wechat_nickname;
	}
	public void setWechat_nickname(String wechat_nickname) {
		this.wechat_nickname = wechat_nickname;
	}
	public String getWechat_sex() {
		return wechat_sex;
	}
	public void setWechat_sex(String wechat_sex) {
		this.wechat_sex = wechat_sex;
	}
	public String getWechat_country() {
		return wechat_country;
	}
	public void setWechat_country(String wechat_country) {
		this.wechat_country = wechat_country;
	}
	public String getWechat_province() {
		return wechat_province;
	}
	public void setWechat_province(String wechat_province) {
		this.wechat_province = wechat_province;
	}
	public String getWechat_headimgurl() {
		return wechat_headimgurl;
	}
	public void setWechat_headimgurl(String wechat_headimgurl) {
		this.wechat_headimgurl = wechat_headimgurl;
	}
	public String getWechat_privilege() {
		return wechat_privilege;
	}
	public void setWechat_privilege(String wechat_privilege) {
		this.wechat_privilege = wechat_privilege;
	}
	public String getWechat_unionid() {
		return wechat_unionid;
	}
	public void setWechat_unionid(String wechat_unionid) {
		this.wechat_unionid = wechat_unionid;
	}
	
}
