package com.core.controller;

import com.core.entity.Node;
import com.core.entity.PageBean;
import com.core.service.LineService;
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


/** 关系操作 */
@Controller
@RequestMapping("/line")
public class LineController {

	@Resource
	private LineService lineService;
	private static final Logger log = Logger.getLogger(LineController.class);// 日志文件

	/** 查询节点列表 */
	@RequestMapping("/list")
	public String list(@RequestParam(value = "page", required = false) String page,
                       @RequestParam(value = "rows", required = false) String rows, Node s_node, HttpServletResponse response) throws Exception {
		PageBean pageBean = new PageBean(Integer.parseInt(page), Integer.parseInt(rows));
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("nodeName", s_node.getNodeNames());
		map.put("labels", s_node.getLabel());
		map.put("start", pageBean.getStart());
		map.put("size", pageBean.getPageSize());
		List<Node> nodeList = lineService.findNode(map);
		Long total = lineService.getTotalNode(map);
		JSONObject result = new JSONObject();
		JSONArray jsonArray = JSONArray.fromObject(nodeList);
		result.put("rows", jsonArray);
		result.put("total", total);
		ResponseUtil.write(response, result);
		return null;
	}

	/** 建立关系或者删除关系 */
	@RequestMapping("/deleteOrAdd")
	public String deleteOrAdd(@RequestParam(value = "ids") String ids, @RequestParam(value = "ids2") String ids2,
			@RequestParam(value = "type") String type, HttpServletResponse response) throws Exception {
		JSONObject result = new JSONObject();
		// 节点ID和节点类型组成的 【111-单位,222-阶段】 字符串
		String[] idLabelsStr = ids.split(",");
		String[] idsLabelStr2 = ids2.split(",");
		int count = 0;
		String message = "";
		// 建立关系 type="1"时为建立关系， type="2"时为解除关系
		if ("1".equals(type)) {
			for (String idLabels : idLabelsStr) {
				for (String idLabels2 : idsLabelStr2) {
					count += lineService.addLine(idLabels, idLabels2);
				}
			}
			message = "建立关系成功<font color=red>" + count + "</font>条！";
			// 解除关系
		} else if ("2".equals(type)) {
			for (String idLabels : idLabelsStr) {
				for (String idLabels2 : idsLabelStr2) {
					count += lineService.deleteLine(idLabels, idLabels2);
				}
			}
			message = "解除关系成功<font color=red>" + count + "</font>条！";
		}

		if (count > 0) {
			result.put("success", true);
			result.put("message", message);
		} else {
			result.put("success", false);
		}
		log.info("1".equals(type) ? "建立关系成功！" : "解除关系成功！" + idLabelsStr + idsLabelStr2 + count);
		ResponseUtil.write(response, result);
		return null;
	}
}
