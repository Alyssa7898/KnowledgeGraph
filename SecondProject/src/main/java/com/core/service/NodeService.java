package com.core.service;

import com.core.entity.Node;

import java.util.List;
import java.util.Map;

public interface NodeService {

	/**
	 * 查找
	 */
	public List<Node> findNode(Map<String, Object> map);

	/**
	 * 查询总数
	 */
	public Long getTotalNode(Map<String, Object> map);

	/**
	 * 添加
	 */
	public int addNode(Node user);

	/**
	 * 修改
	 */
	public int updateNode(Node node);

	/**
	 * 删除
	 */
	public int deleteNode(Integer id);

	/**
	 * 查询节点是否存在
	 * */
	public boolean findNodeExis(String name, String label);
}
