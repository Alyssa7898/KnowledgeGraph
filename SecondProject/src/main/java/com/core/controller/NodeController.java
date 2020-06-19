package com.core.controller;

import com.core.entity.Node;
import com.core.entity.PageBean;
import com.core.service.NodeService;
import com.core.util.ResponseUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** 节点操作 */
@Controller
@RequestMapping("/node")
public class NodeController {

	@Resource
	private NodeService nodeService;
	private static final Logger log = Logger.getLogger(NodeController.class);// 日志文件

	/**
	 * 查询节点列表
	 * */
	@RequestMapping("/list")
	public String list(@RequestParam(value = "page", required = false) String page,
                       @RequestParam(value = "rows", required = false) String rows, @RequestParam(value = "acnode", required = false) String acnode,
                       Node s_node, HttpServletResponse response) throws Exception {
		PageBean pageBean = new PageBean(Integer.parseInt(page), Integer.parseInt(rows));
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("nodeName", s_node.getNodeNames());
		map.put("labels", s_node.getLabel());
		map.put("start", pageBean.getStart());
		map.put("size", pageBean.getPageSize());
		// acnode为是否孤点查询
		map.put("acnode", acnode);
		List<Node> nodeList = nodeService.findNode(map);
		Long total = nodeService.getTotalNode(map);
		JSONObject result = new JSONObject();
		JSONArray jsonArray = JSONArray.fromObject(nodeList);
		result.put("rows", jsonArray);
		result.put("total", total);
		ResponseUtil.write(response, result);
		return null;
	}

	/**
	 * 添加或修改 添加是需判断是否存在
	 */
	@RequestMapping("/save")
	public String save(Node node, HttpServletResponse response) throws Exception {
		String result = "";
		int resultTotal = 0;
		if (node.getId() == null) {
			// 判断节点是否存在
			boolean flag = nodeService.findNodeExis(node.getNodeNames(), node.getLabel());
			if (!flag) {
				result = "当前节点已存在不允许添加！";
			} else {
				resultTotal = nodeService.addNode(node);
				if (resultTotal > 0) {
					result = "添加成功！";
				} else {
					result = "添加失败！";
				}
			}
		} else {
			resultTotal = nodeService.updateNode(node);
			result = "修改数据成功！";
		}
		log.info("添加或修改节点成功！" + node);
		ResponseUtil.write(response, result);
		return null;
	}

	/**
	 * 删除，如果节点存在关系则一并删除
	 */
	@RequestMapping("/delete")
	public String delete(@RequestParam(value = "ids") String ids, @RequestParam(value = "ids") String rs, HttpServletResponse response)
			throws Exception {
		JSONObject result = new JSONObject();
		String[] idsStr = ids.split(",");
		for (int i = 0; i < idsStr.length; i++) {
			nodeService.deleteNode(Integer.parseInt(idsStr[i]));
		}
		result.put("success", true);
		log.info("删除节点成功！" + idsStr);
		ResponseUtil.write(response, result);
		return null;
	}
}
