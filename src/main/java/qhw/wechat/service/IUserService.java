package qhw.wechat.service;

import java.util.Map;

import qhw.wechat.entity.meilian.UserBean;

public interface IUserService {
	int addUser(UserBean user);
	UserBean selectById(Integer id);
	UserBean selectByOpenid(String openid);
	int updateUser(UserBean user);
}
