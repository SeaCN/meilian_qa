package qhw.wechat.controller.meilian;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import qhw.wechat.constant.MessageConst;
import qhw.wechat.entity.meilian.FeedbackBean;
import qhw.wechat.service.IFeedbackService;
import qhw.wechat.service.ITemplateMsgService;
import qhw.wechat.util.Result;

@Controller
@RequestMapping(value = "/feedback")
public class FeedbackController {
	private static final Logger logger = LoggerFactory.getLogger(FeedbackController.class);
	@Autowired
	private IFeedbackService feedbackService;
	@Resource
	private ITemplateMsgService templateServiceImpl;
	/**
	 * 回复建议	
	 * @param request
	 * @param response
	 * @param feedback
	 * @return
	 */
	@RequestMapping(value = "/addFeedback")
	@ResponseBody
	public Result addFeedback(HttpServletRequest request, HttpServletResponse response,
			@RequestBody FeedbackBean feedback){
		if (feedback.getUserid() == null || feedback.getSuggestionid() == null) {
			return new Result(MessageConst.MSG_FAIL_STATUS, MessageConst.MSG_ERROR);
		}
		int effect = 0;
		try {
			effect = this.feedbackService.addFeedback(feedback);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("occur error when addSuggestion()", e);
			return new Result(MessageConst.MSG_FAIL_STATUS, MessageConst.MSG_ERROR);
		}
		if (effect > 0) {
			return new Result(MessageConst.MSG_SUCCESS_STATUS, MessageConst.MSG_SUCCESS_SUBMIT);
		}
		return new Result(MessageConst.MSG_FAIL_STATUS, MessageConst.MSG_FAIL_SUBMIT);
	}
	
	/**
	 * 查询自己所有的回复信息
	 * @param request
	 * @param response
	 * @param userid
	 * @return
	 */
	@RequestMapping(value = "/selectByUserid")
	@ResponseBody
	public Result selectByUserid(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(defaultValue="1") Integer currentPage,
			@RequestParam(defaultValue="10") Integer pageSize,
			@RequestParam(required=false) String title,
			@RequestParam(required=false) String content,
			@RequestParam(required=false) String answer,
			@RequestParam(required=false) Date startDate,
			@RequestParam(required=false) Date endDate,
			@RequestParam Integer userId){
		logger.info("select feedbacks whose author is {}", userId);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("title", title);
		params.put("content", content);
		params.put("startDate", startDate);
		params.put("endDate", endDate);
		params.put("userId", userId);
		params.put("answer", answer);
		int total = 0;
		try {
			total = this.feedbackService.selectTotolNum(params);
		} catch (Exception e) {
			logger.error("find total num of log info occur error!", e);
			return new Result(MessageConst.MSG_FAIL_STATUS, MessageConst.MSG_ERROR);
		}
		int pages = total/pageSize;//总页码
		if(total%pageSize != 0){
			pages = total/pageSize + 1;
		}
		params.put("startNum", (currentPage-1)*pageSize);
		params.put("pageSize", pageSize);
		List<Map<String, Object>> list = null;
		try {
			list = this.feedbackService.selectByPage(params);
		} catch (Exception e) {
			logger.error("find error info occur error!", e);
			return new Result(MessageConst.MSG_FAIL_STATUS, MessageConst.MSG_ERROR);
		}
		
		return new Result(MessageConst.MSG_SUCCESS_STATUS, MessageConst.MSG_SUCCESS_SUBMIT, list);
	}
	
}
