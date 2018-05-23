package qhw.wechat.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import qhw.wechat.entity.CustomerServiceEntity;
import qhw.wechat.token.AccessToken;
import qhw.wechat.util.HttpUtil;
import qhw.wechat.util.PropertyPlaceholder;

@Controller
public class CustomerServiceController {
	private static final Logger logger = LoggerFactory.getLogger(CustomerServiceController.class);
	private static final String add_url = "https://api.weixin.qq.com/customservice/kfaccount/add?access_token=%s";
	
	/**
	 * 添加客服账号	
	 * @param kf_account
	 * @param nickname
	 * @param password
	 */
	@RequestMapping(value = "/addCustomerService")
	public Map<String, Object> addCustomerService(@RequestParam(required = true) String kf_account,
			@RequestParam(required = true) String nickname,
			@RequestParam(required = true) String password){
		CustomerServiceEntity serviceEntity = new CustomerServiceEntity();
		serviceEntity.setKf_account(kf_account);
		serviceEntity.setNickname(nickname);
		serviceEntity.setPassword(password);
		
		String jsonParam = JSONObject.toJSONString(serviceEntity);
		String accessToken = null;
		try {
			accessToken = AccessToken.getAccessToken();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			logger.error("occur error in addCustomerService()", e1);
		}
		/*String accessToken = null;
		try {
			accessToken = AccessToken.updateAccessToken();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}*/
		String url = String.format(add_url, accessToken);
		String response = null;
		try {
			logger.info("url:"+url+",param:"+jsonParam);
			response = HttpUtil.post(url, jsonParam);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("occur error when addCustomerService");
		}
		logger.info("addCustomerService response:" + response);
		JSONObject responseJson = JSONObject.parseObject(response);
		/*//accessToken过期		
		if (responseJson.containsKey("errcode") && responseJson.getIntValue("errcode") == 42001) {
			
		}*/
		return (Map<String, Object>)JSON.parse(response);
	}
}
