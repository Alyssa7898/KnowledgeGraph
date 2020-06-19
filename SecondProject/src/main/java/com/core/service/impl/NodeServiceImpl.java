package com.core.service.impl;

import com.core.entity.Node;
import com.core.service.NodeService;
import com.core.util.JdbcUtil;
import com.core.util.LabelsUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service("nodeService")
public class NodeServiceImpl implements NodeService {

	/***
	 * 查询列表
	 */
	@SuppressWarnings("unchecked")
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
			// 孤点查询
			if ("1".equals(map.get("acnode"))) {
				cql += "match (na" + lab + ") with 0 as count,na where not (na)--() and not 'User' in labels(na) " + where
						+ " return na, count ";
			} else {
				cql += "match (na" + lab + ")-[r]-() where not 'User' in labels(na)  " + where + " return na, count(r) as count ";
			}
			cql += "order by labels(na)[0], count desc skip {1} limit {2} ";
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

					/*
					 * // 节点权重字段可能为空 String _symbolSize =
					 * String.valueOf(result3.get("symbolSize")); if ("null" ==
					 * _symbolSize) { _symbolSize = ""; }
					 * node.setSymbolSize(_symbolSize);
					 */
					// 转换为汉字页面展示
					node.setLabel(LabelsUtil.toChinese(((List<String>) result3.get("_labels")).get(0)));
					// 节点关系数量
					node.setRelationshipCount(Integer.parseInt(result2.get("count") == null ? "0" : result2.get("count").toString()));
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
			// 孤点查询
			if ("1".equals(map.get("acnode"))) {
				cql += "match (na" + lab + ") where not (na)--() and not 'User' in labels(na) ";
			} else {
				cql += "match (na" + lab + ") where not 'User' in labels(na) and (na)--() ";
			}
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

	/** 更新节点 */
	@Override
	public int updateNode(Node node) {
		JdbcUtil jdbcUtil = new JdbcUtil();
		jdbcUtil.getConnection();
		int result = 0;
		try {
			// 查询结果集
			result = jdbcUtil.update("MATCH (na) where not 'User' in labels(na) and  id(na)={1} set na.name={2}, na.symbolSize={3} ",
					node.getId(), node.getNodeNames(), node.getSymbolSize());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != jdbcUtil) {
				jdbcUtil.close();
			}
		}
		return 1;
	}

	/** 添加节点 */
	@Override
	public int addNode(Node node) {
		JdbcUtil jdbcUtil = new JdbcUtil();
		jdbcUtil.getConnection();
		int result = 0;
		try {
			// 不能添加用户
			if (!"User".equals(node.getLabel()) && !"".equals(node.getLabel()) && null != node.getLabel()) {
				result = jdbcUtil.update("create (na:" + node.getLabel() + "{name:{1}, symbolSize:{2}}) return na", node.getNodeNames()
						.trim(), node.getSymbolSize());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != jdbcUtil) {
				jdbcUtil.close();
			}
		}
		return result;
	}

	/** 删除节点 和节点的关系 */
	@Override
	public int deleteNode(Integer id) {
		JdbcUtil jdbcUtil = new JdbcUtil();
		jdbcUtil.getConnection();
		int result = 0;
		try {
			jdbcUtil.update("MATCH (na) WHERE ID(na)={1} DETACH DELETE na", id);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != jdbcUtil) {
				jdbcUtil.close();
			}
		}
		return result;
	}

	/**
	 * 判断节点是否存在
	 */
	@Override
	public boolean findNodeExis(String name, String label) {
		JdbcUtil jdbcUtil = new JdbcUtil();
		jdbcUtil.getConnection();
		List<Map<String, Object>> result = null;
		String cql = "match (na:" + label + ") where na.name={1} return na";
		try {
			result = jdbcUtil.findList(cql, name);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != jdbcUtil) {
				jdbcUtil.close();
			}
		}
		if (null != result && result.size() > 0) {
			return false;
		} else {
			return true;
		}
	}
}
