package qhw.wechat.dao;

import java.util.Map;

import qhw.wechat.entity.meten.UserBean;

public interface IUserDao {
	int addUser(UserBean user);
	UserBean selectById(Integer id);
	UserBean selectByOpenid(String openid);
	int updateUser(UserBean user);
}
