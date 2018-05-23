package qhw.wechat.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import qhw.wechat.dao.ISuggestionDao;
import qhw.wechat.entity.meilian.SuggestionBean;
import qhw.wechat.service.ISuggestionService;

@Service
public class SuggestionServiceImpl implements ISuggestionService {
	@Autowired
	private ISuggestionDao dao;
	
	@Override
	public int addSugg(SuggestionBean suggestion) {
		// TODO Auto-generated method stub
		return this.dao.addSugg(suggestion);
	}

	@Override
	public List<SuggestionBean> selectAll() {
		// TODO Auto-generated method stub
		return this.dao.selectAll();
	}

	@Override
	public List<Map<String, Object>> selectByUserid(int userid) {
		// TODO Auto-generated method stub
		return this.dao.selectByUserid(userid);
	}

	@Override
	public int updateSugg(SuggestionBean suggestion) {
		// TODO Auto-generated method stub
		return this.dao.updateSugg(suggestion);
	}

	@Override
	public List<Map<String, Object>> selectByParams(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return this.dao.selectByParams(params);
	}

	@Override
	public SuggestionBean selectById(Integer id) {
		// TODO Auto-generated method stub
		return this.dao.selectById(id);
	}

	@Override
	public int selectTotolNum(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return this.dao.selectTotolNum(params);
	}

	@Override
	public List<Map<String, Object>> selectByPage(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return this.dao.selectByPage(params);
	}

}
