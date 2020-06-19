package com.core.service.impl;

import com.core.entity.Node;
import com.core.service.LineService;
import com.core.util.JdbcUtil;
import com.core.util.LabelsUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service("lineService")
public class LineServiceImpl implements LineService {

	/***
	 * 查询列表
	 */
	@Override
	public List<Node> findNode(Map<String, Object> map) {
		JdbcUtil jdbcUtil = new JdbcUtil();
		jdbcUtil.getConnection();
		List<Map<String, Object>> result = null;
		String lab = "";

		// 如果选择了标签就带上标签查询
		if (!"".equals(map.get("labels")) && map.get("labels") != null) {
			lab = ":" + map.get("labels");
		}

		// 如果输入了节点名称就模糊匹配节点名称查询
		String where = "";
		if (!"".equals(map.get("nodeName")) && null != map.get("nodeName")) {
			where = "and na.name=~'.*" + map.get("nodeName") + ".*' ";
		}
		try {
			String cql = "";
			cql += "match (na" + lab + ") where not 'User' in labels(na) " + where;
			cql += "return na order by labels(na)[0], na.name desc skip {1} limit {2} ";
			result = jdbcUtil.findList(cql, map.get("start"), map.get("size"));
			// 查询结果集
			// 遍历结果集封装List<Node>返回
			if (null != result) {
				List<Node> arrayList = new ArrayList<Node>();
				for (Map<String, Object> result2 : result) {
					Node node = new Node();
					Map<String, Object> result3 = (Map<String, Object>) result2.get("na");
					node.setId(Integer.valueOf(result3.get("_id").toString()));
					node.setNodeNames(String.valueOf(result3.get("name")));
					// 转换为汉字页面展示
					node.setLabel(LabelsUtil.toChinese(((List<String>) result3.get("_labels")).get(0)));
					arrayList.add(node);
				}
				return arrayList;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != jdbcUtil) {
				jdbcUtil.close();
			}
		}
		return null;
	}

	/**
	 * 查询分页总数
	 * */
	@Override
	public Long getTotalNode(Map<String, Object> map) {
		JdbcUtil jdbcUtil = new JdbcUtil();
		jdbcUtil.getConnection();
		Long result2 = 0l;
		String lab = "";

		// 如果选择了标签就带上标签查询
		if (!"".equals(map.get("labels")) && map.get("labels") != null) {
			lab = ":" + map.get("labels");
		}

		// 如果输入了节点名称就模糊匹配节点名称查询
		String where = "";
		if (!"".equals(map.get("nodeName")) && null != map.get("nodeName")) {
			where = "and na.name=~'.*" + map.get("nodeName") + ".*'";
		}

		try {
			String cql = "";
			cql += "match (na" + lab + ") where not 'User' in labels(na) ";
			cql += where + " return count(na) as count ";
			result2 = (Long) jdbcUtil.findList(cql).get(0).get("count");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != jdbcUtil) {
				jdbcUtil.close();
			}
		}
		return result2;
	}

	/** 建立关系 */
	@Override
	public int addLine(String idLabels, String idLabels2) {
		String[] split = idLabels.split("-");
		String[] split2 = idLabels2.split("-");

		String typer = LabelsUtil.labelToLine(split[1], split2[1]);

		JdbcUtil jdbcUtil = new JdbcUtil();
		jdbcUtil.getConnection();
		int result = 0;
		try {
			String cql = "match (aa), (bb) where id(aa)={1} and id(bb)={2} create (aa)-[:" + typer + "{name:''}]->(bb) ";
			result = jdbcUtil.update(cql, Integer.parseInt(split[0]), Integer.parseInt(split2[0]));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != jdbcUtil) {
				jdbcUtil.close();
			}
		}
		return result;
	}

	/** 解除关系 */
	@Override
	public int deleteLine(String idLabels, String idLabels2) {
		String[] split = idLabels.split("-");
		String[] split2 = idLabels2.split("-");
		JdbcUtil jdbcUtil = new JdbcUtil();
		jdbcUtil.getConnection();
		int result = 0;
		try {
			String cql = "match (aa)-[r]-(bb) where id(aa)={1} and id(bb)={2} delete r ";
			result = jdbcUtil.update(cql, Integer.parseInt(split[0]), Integer.parseInt(split2[0]));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != jdbcUtil) {
				jdbcUtil.close();
			}
		}
		return result;
	}
}
