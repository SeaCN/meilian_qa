package qhw.wechat.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.io.filefilter.RegexFileFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import qhw.wechat.entity.BaseMsgEntity;
import qhw.wechat.entity.TextMsgEntity;
import qhw.wechat.util.PassiveReplyMsgUtil;
import qhw.wechat.util.Sha1Util;

@Controller
public class IndexController {
	private static final Logger logger = LoggerFactory.getLogger(IndexController.class);
	private static final String token = "meten";
	
	/**
	 * 入口	
	 * @param request
	 * @param response
	 * @param signature
	 * @param timestamp
	 * @param nonce
	 * @param echostr
	 */
	@RequestMapping(value = "/index")
	public void index(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(required=false) String signature,
			@RequestParam(required=false) String timestamp,
			@RequestParam(required=false) String nonce,
			@RequestParam(required=false) String echostr) throws Exception{
		//接入请求	
		if (null != signature && null != timestamp && null != nonce && null != echostr) {
			responseBind(response, echostr, signature, timestamp, nonce);
			return;
		}
		
		//接收普通消息
		ServletInputStream sis = request.getInputStream();
		StringBuilder sb = new StringBuilder();
		int len;
		byte[] buffer = new byte[1024];
		while ((len = sis.read(buffer)) != -1) {
			sb.append(new String(buffer, 0, len, "UTF-8"));
		}
		String xml = sb.toString();
		logger.info("receive xml:" + xml);
		//回复success		
		PrintWriter writer = response.getWriter();
		writer.write("success");
		writer.flush();
		
		//获取消息类型
		Pattern pattern = Pattern.compile("<MsgType><\\!\\[CDATA\\[(.*?)\\]\\]></MsgType>");
		Matcher matcher = pattern.matcher(xml);
		String msgType = null;
		if (matcher.find()) {
			msgType = matcher.group(1);
		}
		//判别消息类型，进行响应处理		
		switch (msgType) {
		case "text":
			JAXBContext context = JAXBContext.newInstance(BaseMsgEntity.class); 
			Unmarshaller unmarshaller = context.createUnmarshaller(); 
			BaseMsgEntity baseEntity = (BaseMsgEntity)unmarshaller.unmarshal(new ByteArrayInputStream(xml.getBytes("UTF-8")));
			String content = "hello," + baseEntity.getFromUserName();
			PassiveReplyMsgUtil.passiveReplyText(response, baseEntity.getFromUserName(), baseEntity.getToUserName(), content);
			break;
		case "image":
			
			break;
		case "voice":
			
			break;
		case "video":
			
			break;
		case "shortvideo":
			
			break;
		case "location":
			
			break;
		case "link":
			
			break;
		case "event":
			
			break;

		default:
			break;
		}
	}
	
	/**
	 * 响应接入请求
	 * @param response
	 * @param echostr
	 */
	public void responseBind(HttpServletResponse response, String echostr, 
			String signature,  String timestamp, String nonce){
		String[] arr = {nonce, timestamp, token};
		Arrays.sort(arr);
		String rawStr = StringUtils.arrayToDelimitedString(arr, "");
		logger.info("receive signature:" + signature);
		String encryStr = null;
		try {
			encryStr = Sha1Util.encry(rawStr);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		logger.info("get encryption:" + encryStr);
		if (encryStr.equals(signature)) {
			PrintWriter writer = null;
			try {
				writer = response.getWriter();
			} catch (IOException e) {
				e.printStackTrace();
				logger.error("", e);
			}
			writer.write(echostr);
		}
	}
	
	public static void main(String[] args) {
		String xml = "<xml><ToUserName><![CDATA[gh_10b53714a4f0]]></ToUserName><FromUserName><![CDATA[oA6gfwxID6lHGVbQte7AcG0K9oe8]]></FromUserName><CreateTime>1520479099</CreateTime><MsgType><![CDATA[text]]></MsgType><Content><![CDATA[太冷了]]></Content><MsgId>6530408004938862540</MsgId></xml>";
		try {
			JAXBContext context = JAXBContext.newInstance(TextMsgEntity.class); 
			Unmarshaller unmarshaller = context.createUnmarshaller(); 
			Object object = unmarshaller.unmarshal(new ByteArrayInputStream(xml.getBytes()));
			System.out.println(object);
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
