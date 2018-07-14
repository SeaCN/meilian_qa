package qhw.wechat.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import qhw.wechat.constant.MessageConst;
import qhw.wechat.token.AccessToken;
import qhw.wechat.token.JsapiTicket;
import qhw.wechat.util.HttpUtil;
import qhw.wechat.util.Result;
import util.speex.SpeexUtils;

@Controller
@RequestMapping(value = "/jssdk")
public class JssdkController {
	private static final Logger logger = LoggerFactory.getLogger(JssdkController.class);
	private static final String mediaUrl = "https://api.weixin.qq.com/cgi-bin/media/get/jssdk?access_token=%s&media_id=%s";

	@RequestMapping(value = "/sign")
	@ResponseBody
	public Result calcSign(HttpServletRequest request, @RequestParam String url) {
		// 获取jsapi_ticket
		String jsapi_ticket;
		try {
			jsapi_ticket = JsapiTicket.getJsapi();
			logger.info("get jsapi_ticket:{}", jsapi_ticket);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("occur error while JsapiTicket.getJsapi()");
			return new Result(MessageConst.MSG_FAIL_STATUS, MessageConst.MSG_ERROR);
		}
		int noncestr = new Random().nextInt();
		Long timestamp = System.currentTimeMillis() / 1000;

		List<String> nameList = new ArrayList<String>();
		nameList.add("noncestr");
		nameList.add("timestamp");
		nameList.add("url");
		nameList.add("jsapi_ticket");
		Collections.sort(nameList);

		Map<String, Object> valueMap = new HashMap<String, Object>();
		valueMap.put("noncestr", noncestr);
		valueMap.put("timestamp", timestamp);
		valueMap.put("url", url);
		valueMap.put("jsapi_ticket", jsapi_ticket);

		String origin = "";
		for (int i = 0; i < nameList.size(); i++) {
			origin += nameList.get(i) + "=" + valueMap.get(nameList.get(i)).toString() + "&";
		}
		origin = origin.substring(0, origin.length() - 1);
		String sign = DigestUtils.sha1Hex(origin);
		logger.info("calc sign:{}", sign);
		Map<String, String> result = new HashMap<String, String>();
		result.put("sign", sign);
		result.put("noncestr", noncestr + "");
		result.put("timestamp", timestamp + "");
		result.put("appId", AccessToken.appid);

		return new Result(MessageConst.MSG_SUCCESS_STATUS, MessageConst.MSG_SUCCESS_SUBMIT, result);
	}

	/**
	 * 通过 获取临时素材接口 下载通过JSSDK上传的录音，并转码为wav格式
	 * 
	 * @param request
	 * @param serverId
	 * @return
	 */
	@RequestMapping(value = "/getAudio")
	@ResponseBody
	public Result getAudio(HttpServletRequest request, @RequestParam String serverId) {
		Map<String, String> result = new HashMap<String, String>();
		try {
			String accessToken = AccessToken.getAccessToken();
			String url = String.format(JssdkController.mediaUrl, accessToken, serverId);
			String response = HttpUtil.get(url);
			logger.info("save to local:{}", response);
			// 解码speex为wav保存到服务器
			String wavPath = response.substring(0, response.lastIndexOf(".") - 1) + ".wav";
			try {
				SpeexUtils.decode(response, wavPath);
				logger.info("decode to file:{}", wavPath);
				// 保存转码后的文件路径到数据库
				result.put("wavPath", wavPath);
				return new Result(MessageConst.MSG_SUCCESS_STATUS, MessageConst.MSG_SUCCESS_SUBMIT, result);
			} catch (Exception e) {
				// 解压出错
				e.printStackTrace();
				logger.error("decode speex to wav occur error!");
				return new Result(MessageConst.MSG_FAIL_STATUS, MessageConst.MSG_ERROR);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("occur error while getAudio()");
			return new Result(MessageConst.MSG_FAIL_STATUS, MessageConst.MSG_ERROR);
		}
	}
}
