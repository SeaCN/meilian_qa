package qhw.wechat.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;

import qhw.wechat.entity.meilian.UserBean;
import qhw.wechat.service.IUserService;
import qhw.wechat.token.AccessToken;
import qhw.wechat.util.HttpUtil;

@Controller
public class AuthorController {
	private static final Logger logger = LoggerFactory.getLogger(AuthorController.class);
	private static final String state = "meilian";
	
	@Autowired
	private IUserService userService;
	/**
	 * 获取code	
	 * @param request
	 * @param response
	 */
	@RequestMapping("/getCode")
	public String getCode(HttpServletRequest request, HttpServletResponse response){
		response.addHeader("Access-Control-Allow-Origin", "*");
		String redirect_uri = "http://www.devqz.club/wechat_meilian/redirect_uri";
		String scope = "snsapi_base";
		try {
			redirect_uri = URLEncoder.encode(redirect_uri, "utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			logger.error("", e);
		}
		String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid="
				+ AccessToken.appid
				+ "&redirect_uri=" + redirect_uri
				+ "&response_type=code"
				+ "&scope=" + scope
				+ "&state=" + state
				+ "#wechat_redirect";
		logger.info("url:" + url);
		return "redirect:" + url;
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/redirect_uri")
	public String getAccessTokenAndOpenid(HttpServletRequest request, HttpServletResponse response,
			@RequestParam String code, @RequestParam String state){
		String res = null;
		if (state.equals(this.state)) {
			String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type=authorization_code";
			url = String.format(url, AccessToken.appid, AccessToken.secret, code);
			try {
				res = HttpUtil.get(url);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.error("occur error when getAccessTokenAndOpenid", e);
			}
		}
		logger.info("res:" + res);
		JSONObject jsonObject = JSONObject.parseObject(res);
		String openid = jsonObject.getString("openid");
		//查库确定用户是否已经注册，若已注册直接跳转到意见提交页面，无则先跳到注册页面
		UserBean user = this.userService.selectByOpenid(openid);
		if (user == null) {
			return "redirect:/meilian/index.html#regist?openid="+openid;
		}else {
			int userId = user.getId();
			return "redirect:/meilian/index.html#suggest?userId="+userId;
		}
	}
}
