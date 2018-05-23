package qhw.wechat.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import qhw.wechat.service.ITemplateMsgService;
import qhw.wechat.token.AccessToken;
import qhw.wechat.util.HttpUtil;

@Service
public class TemplateServiceImpl implements ITemplateMsgService{
	private static final Logger logger = LoggerFactory.getLogger(TemplateServiceImpl.class);
	
	private static final String TEMPLATE_GET_URL = "https://api.weixin.qq.com/cgi-bin/template/api_add_template?access_token=%s";
	private static final String TEMPLATE_SEND_URL = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=%s";
	
	private static final String TEMPLATE_GET_ID = "{\"template_id_short\":\"%s\"}";
	private static final String TEMPLATE_SEND_MSG = "{\"touser\":\"%s\",\"template_id\":\"%s\",\"url\":\"http://www.baidu.com\",\"data\":{\"NAME\":{\"value\":\"%s\",\"color\":\"%s\"}}}";
	
	
	@Override
	public String sendTemplateMsg(String ... params) {
		String accessToken = null;
		try {
			accessToken = AccessToken.getAccessToken();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			logger.error("occur error in addCustomerService()", e1);
		}
		String url = String.format(TEMPLATE_SEND_URL, accessToken);
		String template = String.format(TEMPLATE_SEND_MSG, params);
		String response = "";
		try {
			response = HttpUtil.post(url, template);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return response;
	}

}
