package qhw.wechat.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import qhw.wechat.service.ITemplateMsgService;
import qhw.wechat.token.AccessToken;
import qhw.wechat.util.HttpUtil;

@Controller
@RequestMapping(value = "/template")
public class TemplateMsgController {
	private static Logger logger = LoggerFactory.getLogger(TemplateMsgController.class);
	
	private static final String TEMPLATE_GET_URL = "https://api.weixin.qq.com/cgi-bin/template/api_add_template?access_token=%s";
	private static final String TEMPLATE_SEND_URL = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=%s";
	
	private static final String TEMPLATE_GET_ID = "{\"template_id_short\":\"%s\"}";
	private static final String TEMPLATE_SEND_MSG = "{\"touser\":\"%s\",\"template_id\":\"%s\",\"url\":\"http://www.baidu.com\",\"data\":{\"NAME\":{\"value\":\"%s\",\"color\":\"%s\"}}}";
	
	@Resource
	private ITemplateMsgService templateServiceImpl;
	/**
	 * 发送模板消息
	 * @param request
	 * @param touser		发送给谁
	 * @param template_id	模板消息的id
	 * @param name			内容参数
	 * @param color			字体颜色参数
	 */
	@RequestMapping(value = "/sendMsg")
	public void sendMsg(HttpServletRequest request,
			@RequestParam(defaultValue = "oA6gfw2bYJi5WtZ2X5miJVXsxZZM") String touser,
			@RequestParam(defaultValue = "vpsVATJWtVl8PgWikY8DHdqFB7DiXLVwjUyfnep7cS4") String template_id,
			@RequestParam(defaultValue = "Tom") String name,
			@RequestParam(defaultValue = "#FF6B62") String color){
		String response = templateServiceImpl.sendTemplateMsg(touser, template_id, name, color);
		logger.info("response:{}", response);
	}
	
	@RequestMapping(value = "/getTemplate")
	public void getTemplate(HttpServletRequest request,
			@RequestParam String No){
		String accessToken = null;
		try {
			accessToken = AccessToken.getAccessToken();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			logger.error("occur error in addCustomerService()", e1);
		}
		String url = String.format(TEMPLATE_GET_URL, accessToken);
		String template = String.format(TEMPLATE_GET_ID, No);
		String response = templateServiceImpl.sendTemplateMsg(url, template);
		
		logger.info("response:{}", response);
	}
}
