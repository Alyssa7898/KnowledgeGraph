package com.core.service;

import com.core.entity.User;

import java.util.List;
import java.util.Map;

public interface UserService {

	/**
	 * 登陆
	 */
	public User login(User user);

	/**
	 * 查找用户
	 */
	public List<User> findUser(Map<String, Object> map);

	/**
	 * 查询用户总数
	 */
	public Long getTotalUser(Map<String, Object> map);

	/**
	 * 修改密码
	 */
	public int updateUser(User user);

	/**
	 * 添加用户
	 */
	public int addUser(User user);

	/**
	 * 删除用户
	 */
	public int deleteUser(Integer id);

	/** 首页展示节点数量 */
	public Map<String, String> findLabels();
}
