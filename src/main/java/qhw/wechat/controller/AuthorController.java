package qhw.wechat.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;

import qhw.wechat.constant.MessageConst;
import qhw.wechat.entity.meten.UserBean;
import qhw.wechat.service.IUserService;
import qhw.wechat.token.AccessToken;
import qhw.wechat.util.HttpUtil;
import qhw.wechat.util.Result;

@Controller
public class AuthorController {
	private static final Logger logger = LoggerFactory.getLogger(AuthorController.class);
	private static final String state = "meten";
	
	@Autowired
	private IUserService userService;
	
	@RequestMapping("/checkLogin")
	@ResponseBody
	public Result checkLogin(HttpServletRequest request, HttpServletResponse response){
		Object object = request.getSession().getAttribute("cuser");
		if (null != object) {
			return new Result(MessageConst.MSG_SUCCESS_STATUS, MessageConst.MSG_SUCCESS_SUBMIT, 1);
		}else {
			return new Result(MessageConst.MSG_SUCCESS_STATUS, MessageConst.MSG_SUCCESS_SUBMIT, 0);
		}
	}
	/**
	 * 获取code	
	 * @param request
	 * @param response
	 */
	@RequestMapping("/getCode")
	public String getCode(HttpServletRequest request, HttpServletResponse response){
		String redirect_uri = "http://java.devqz.club/meten/redirect_uri?to=" + request.getParameter("to");
		String scope = "snsapi_base";
		try {
			redirect_uri = URLEncoder.encode(redirect_uri, "utf-8");
		} catch (UnsupportedEncodingException e) {
			logger.error("occur error while encode", e);
		}
		String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid="
				+ AccessToken.appid
				+ "&redirect_uri=" + redirect_uri
				+ "&response_type=code"
				+ "&scope=" + scope
				+ "&state=" + AuthorController.state
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
		String to = request.getParameter("to");
		String res = null;
		if (state.equals(AuthorController.state)) {
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
		request.getSession().setAttribute("openId", openid);
		//查库确定用户是否已经注册，若已注册直接跳转到意见提交页面，无则先跳到注册页面
		UserBean user = this.userService.selectByOpenid(openid);
		if (user == null) {
			return "redirect:http://web.devqz.club/#/Regist?to="+to;// 跳转到注册页面
		}else {
			request.getSession().setAttribute("cuser", user);
			return "redirect:http://web.devqz.club/#" + to;
		}
	}
}
