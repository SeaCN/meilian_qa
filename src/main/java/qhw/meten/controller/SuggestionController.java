package qhw.meten.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


import qhw.wechat.constant.MessageConst;
import qhw.wechat.entity.meten.SuggestionBean;
import qhw.wechat.entity.meten.UserBean;
import qhw.wechat.service.ISuggestionService;
import qhw.wechat.util.Result;

/**
 * 用户建议相关操作
 * @author Q
 *
 */
@Controller
@RequestMapping(value = "/suggestion")
public class SuggestionController {
	private final static Logger logger = LoggerFactory.getLogger(SuggestionController.class);
	@Autowired
	private ISuggestionService suggestionService;
	
	@RequestMapping(value = "/addSuggView")
	public String addSuggestionView(HttpServletRequest request, HttpServletResponse response) {
		return "redirect:http://web.devqz.club/#/CommonUserPage?selected=2";
	}
	/**
	 * 提交意见	
	 * @param request
	 * @param response
	 * @param suggestion
	 */
	@RequestMapping(value = "/addSugg")
	@ResponseBody
	public Result addSuggestion(HttpServletRequest request, HttpServletResponse response,
			@RequestBody SuggestionBean suggestion){
		Object object = request.getSession().getAttribute("cuser");
		if (null == object) {
			logger.error("invalid request without userinfo in session...");
			return new Result(MessageConst.MSG_FAIL_STATUS, MessageConst.MSG_ERROR);
		}
		suggestion.setUserid(((UserBean)object).getId());
		int effect = 0;
		try {
			effect = this.suggestionService.addSugg(suggestion);
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
	 * 修改意见	
	 * @param request
	 * @param response
	 * @param suggestion
	 */
	@RequestMapping(value = "/updateSugg")
	@ResponseBody
	public Result updateSuggestion(HttpServletRequest request, HttpServletResponse response,
			@RequestBody SuggestionBean suggestion){
		if (suggestion.getId() == null) {
			return new Result(MessageConst.MSG_FAIL_STATUS, MessageConst.MSG_ERROR);
		}
		int effect = 0;
		try {
			effect = this.suggestionService.updateSugg(suggestion);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("occur error when updateSuggestion()", e);
			return new Result(MessageConst.MSG_FAIL_STATUS, MessageConst.MSG_ERROR);
		}
		if (effect > 0) {
			return new Result(MessageConst.MSG_SUCCESS_STATUS, MessageConst.MSG_SUCCESS_SUBMIT);
		}
		return new Result(MessageConst.MSG_FAIL_STATUS, MessageConst.MSG_FAIL_SUBMIT);
	}
	
	/**
	 * 查询所有建议(用于管理员查看,只显示未回复的建议)
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/selectAll")
	@ResponseBody
	public Result selectAll(HttpServletRequest request, HttpServletResponse response){
		List<SuggestionBean> list = null;
		try {
			list = this.suggestionService.selectAll();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("occur error when selectAll()", e);
			return new Result(MessageConst.MSG_FAIL_STATUS, MessageConst.MSG_ERROR);
		}
		return new Result(MessageConst.MSG_SUCCESS_STATUS, MessageConst.MSG_SUCCESS_SUBMIT, list);
	}
	
	/**
	 * 查询建议
	 * @param request
	 * @param response
	 * @param userid
	 * @return
	 */
	@RequestMapping(value = "/selectByParams")
	@ResponseBody
	public Result selectByUserid(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Integer userid){
		List<Map<String, Object>> list = null;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userid", userid);
		try {
			list = this.suggestionService.selectByParams(params);
		} catch (Exception e) {
			logger.error("occur error when selectAll()", e);
			return new Result(MessageConst.MSG_FAIL_STATUS, MessageConst.MSG_ERROR);
		}
		
		return new Result(MessageConst.MSG_SUCCESS_STATUS, MessageConst.MSG_SUCCESS_SUBMIT, list);
	}
	
	/**
	 * 查询建议
	 * @param request
	 * @param response
	 * @param userid
	 * @return
	 */
	@RequestMapping(value = "/selectById")
	@ResponseBody
	public Result selectById(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Integer id){
		logger.info("select suggestion according to userid...");
		SuggestionBean suggestion = null;
		try {
			suggestion = this.suggestionService.selectById(id);
		} catch (Exception e) {
			logger.error("occur error when selectById()", e);
			return new Result(MessageConst.MSG_FAIL_STATUS, MessageConst.MSG_ERROR);
		}
		
		return new Result(MessageConst.MSG_SUCCESS_STATUS, MessageConst.MSG_SUCCESS_SUBMIT, suggestion);
	}
	
	/**
	 * 分页查询未回复的所有意见
	 * @param request
	 * @param response
	 * @param currentPage
	 * @param pageSize
	 * @param nickname
	 * @param title
	 * @param content
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	@RequestMapping(value = "/suggestionByPage")
	@ResponseBody
	public Result selectByPage(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(defaultValue="1") Integer currentPage,
			@RequestParam(defaultValue="10") Integer pageSize,
			@RequestParam(required=false) String title,
			@RequestParam(required=false) String content,
			@RequestParam(required=false) Date startDate,
			@RequestParam(required=false) Date endDate){
		try {
			Thread.currentThread().sleep(3000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		logger.info("select suggestions which have no feedback...");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("title", title);
		params.put("content", content);
		params.put("startDate", startDate);
		params.put("endDate", endDate);
		int total = 0;
		try {
			total = this.suggestionService.selectTotolNum(params);
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
			list = this.suggestionService.selectByPage(params);
		} catch (Exception e) {
			logger.error("find error info occur error!", e);
			return new Result(MessageConst.MSG_FAIL_STATUS, MessageConst.MSG_ERROR);
		}
		
		return new Result(MessageConst.MSG_SUCCESS_STATUS, MessageConst.MSG_SUCCESS_SUBMIT, list);
	}
}
