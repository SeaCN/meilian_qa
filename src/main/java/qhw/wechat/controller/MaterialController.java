package qhw.wechat.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import qhw.wechat.constant.MessageConst;
import qhw.wechat.token.AccessToken;
import qhw.wechat.util.HttpUtil;
import qhw.wechat.util.Result;

@Controller
@RequestMapping(value = "/material")
public class MaterialController {
	private static final Logger logger = LoggerFactory.getLogger(MaterialController.class);
	
	public static final String GET_TEMP_MATERIAL_URL = "https://api.weixin.qq.com/cgi-bin/media/get?access_token=%s&media_id=%s";
	
	@RequestMapping(value = "/getTemp")
	@ResponseBody
	public Result getTempMaterial(HttpServletRequest request,
			@RequestParam String media_id) {
		Map<String, String> data = new HashMap<String, String>();
		try {
			String token = AccessToken.getAccessToken();
			String url = String.format(GET_TEMP_MATERIAL_URL, token, media_id);
			String response = HttpUtil.get(url);
			logger.info("local media path:" + response);
			data.put("resUrl", response);
		} catch (Exception e) {
			logger.error("occur error while getTempMaterial(...)", e);
			return new Result(MessageConst.MSG_FAIL_STATUS, MessageConst.MSG_FAIL_SUBMIT);
		}
		return new Result(MessageConst.MSG_SUCCESS_STATUS, MessageConst.MSG_SUCCESS_SUBMIT, data);
	}
}
