package com.core.service;

import com.core.entity.Node;

import java.util.List;
import java.util.Map;

public interface LineService {

	/**
	 * 查找
	 */
	public List<Node> findNode(Map<String, Object> map);

	/**
	 * 查询总数
	 */
	public Long getTotalNode(Map<String, Object> map);

	/**
	 * 建立关系
	 */
	public int addLine(String idLabels, String idLabels2);

	/**
	 * 删除关系
	 */
	public int deleteLine(String idLabels, String idLabels2);
}
