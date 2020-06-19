package com.core.controller;

import com.core.service.TcgnicalSupervisionService;
import com.core.util.ResponseUtil;
import net.sf.json.JSON;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/** 图谱查询 */
@Controller
@RequestMapping("/TcgnicalSupervisionController")
public class TcgnicalSupervisionController {
	@Resource
	private TcgnicalSupervisionService service;

	/** 查询 */
	@RequestMapping("/query")
	public String listAll(@RequestParam(value = "Chapter[]", required = false) String[] chapter,
			@RequestParam(value = "Topic[]", required = false) String[] topic,
			@RequestParam(value = "Subject[]", required = false) String[] subject,
						  HttpServletResponse response) throws Exception {
		// 封装查询条件
		ArrayList<String> list = new ArrayList<String>();
		//此集合顺序决定查询顺序
		if (topic != null && topic.length > 0) {
			list.add("Topic~~~" + Arrays.toString(topic));
		}
		if (subject != null && subject.length > 0) {
			list.add("Subject~~~" + Arrays.toString(subject));
		}
		if (chapter != null && chapter.length > 0) {
			list.add("Chapter~~~" + Arrays.toString(chapter));
		}
		JSONObject result = service.find(list);
		ResponseUtil.write(response, result);
		return null;
	}

	/** 初始化下拉框 */
	@RequestMapping("/select")
	public String selectAll(HttpServletResponse response) throws Exception {
		JSONObject result = service.findLables();
		ResponseUtil.write(response, result);
		return null;
	}
	@RequestMapping("/jump")
	public String jumpToPage(@RequestParam(value = "node_name", required = false) String name,
							 @RequestParam(value = "node_type", required = false) String type,
							 @RequestParam(value = "node_id", required = false) String id,
							 HttpSession session,
							 HttpServletResponse response)throws Exception{
		session.setAttribute("nodeName",name);
		session.setAttribute("nodeType",type);
		session.setAttribute("nodeId",id);
		Map map=new HashMap();
		map.put("nodeData",name);
		JSONObject result= JSONObject.fromObject(map);
		ResponseUtil.write(response, result);
		return null;
	}
}
