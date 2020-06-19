package com.core.controller;

import com.core.entity.PageBean;
import com.core.entity.User;
import com.core.service.UserService;
import com.core.util.MD5Util;
import com.core.util.ResponseUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** 用户操作 */
@Controller
@RequestMapping("/user")
public class UserController {

	@Resource
	private UserService userService;
	private static final Logger log = Logger.getLogger(UserController.class);// 日志文件

	/**
	 * 登录
	 */
	@RequestMapping(value = "/login")
	public String login(User user, HttpServletRequest request) {
		try {
			String MD5pwd = MD5Util.MD5Encode(user.getPassword(), "UTF-8");
			user.setPassword(MD5pwd);
		} catch (Exception e) {
			user.setPassword("");
		}
		User resultUser = userService.login(user);
		Map<String, String> resultLabels = userService.findLabels();
		log.info("request: user/login , user: " + user.toString());
		if (resultUser == null) {
			request.setAttribute("user", user);
			request.setAttribute("errorMsg", "请认真核对账号、密码！");
			return "/login";
		} else {
			HttpSession session = request.getSession();
			session.setAttribute("currentUser", resultUser);
			session.setAttribute("resultLabels", resultLabels);
			// MDC.put("userName", user.getUserName());
			if(resultUser.getRole().equals("1"))
				return "redirect:/main.jsp";//重定向
			else
				return "redirect:/main_user.jsp";
		}
	}

	/**
	 * 修改密码
	 */
	@RequestMapping("/modifyPassword")
	public String modifyPassword(User user, HttpServletResponse response) throws Exception {
		String MD5pwd = MD5Util.MD5Encode(user.getPassword(), "UTF-8");
		String MD5NewPwd = MD5Util.MD5Encode(user.getNewPassword(), "UTF-8");
		user.setPassword(MD5pwd);
		user.setNewPassword(MD5NewPwd);

		User login = userService.login(user);
		JSONObject result = new JSONObject();
		if (null != login) {
			user.setPassword(MD5NewPwd);
			int resultTotal = userService.updateUser(user);
			if (resultTotal > 0) {
				result.put("success", true);
			} else {
				result.put("success", false);
			}
		} else {
			result.put("success", false);
		}
		log.info("request: user/modifyPassword , user: " + user.toString());
		ResponseUtil.write(response, result);
		return null;
	}

	/**
	 * 退出系统
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/logout")
	public String logout(HttpSession session) throws Exception {
		session.invalidate();
		log.info("request: user/logout");
		return "redirect:/login.jsp";
	}

	/**
	 * 查询
	 */
	@RequestMapping("/list")
	public String list(@RequestParam(value = "page", required = false) String page,
			@RequestParam(value = "rows", required = false) String rows, User s_user, HttpServletResponse response) throws Exception {
		PageBean pageBean = new PageBean(Integer.parseInt(page), Integer.parseInt(rows));
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userName", s_user.getUserName());
		map.put("start", pageBean.getStart());
		map.put("size", pageBean.getPageSize());
		List<User> userList = userService.findUser(map);
		Long total = userService.getTotalUser(map);
		JSONObject result = new JSONObject();
		JSONArray jsonArray = JSONArray.fromObject(userList);
		result.put("rows", jsonArray);
		result.put("total", total);
		log.info("request: user/list , map: " + map.toString());
		ResponseUtil.write(response, result);
		return null;
	}

	/**
	 * 管理员添加或修改
	 */
	@RequestMapping("/save")
	public String save(User user, HttpServletResponse response) throws Exception {
		String result = "";
		int resultTotal = 0;
		if (user.getPassword() != null && !"".equals(user.getPassword())) {
			String MD5pwd = MD5Util.MD5Encode(user.getPassword(), "UTF-8");
			user.setPassword(MD5pwd);
		}
		if (user.getId() == null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("userName", user.getUserName());
			List<User> list = userService.findUser(map);
			if (list != null && list.size() > 0) {
				result = "当前用户已存在不允许添加！";
			} else {
				resultTotal = userService.addUser(user);
				if (resultTotal > 0) {
					result = "添加用户成功！";
				} else {
					result = "添加用户失败！";
				}
			}
		} else {
			resultTotal = userService.updateUser(user);
			result = "修改数据成功！";
		}
		log.info("request: user/save , map: " + user);
		ResponseUtil.write(response, result);
		return null;
	}

	/**
	 * 管理员删除
	 */
	@RequestMapping("/delete")
	public String delete(@RequestParam(value = "ids") String ids, HttpServletResponse response) throws Exception {
		JSONObject result = new JSONObject();
		String[] idsStr = ids.split(",");
		for (int i = 0; i < idsStr.length; i++) {
			userService.deleteUser(Integer.parseInt(idsStr[i]));
		}
		result.put("success", true);
		log.info("request: user/delete , map: " + idsStr);
		ResponseUtil.write(response, result);
		return null;
	}
}
