package com.core.entity;

/** 节点实体类 */
public class Node {

	@Override
	public String toString() {
		return "Node [id=" + id + ", relationshipCount=" + relationshipCount + ", nid=" + nid + ", nodeNames=" + nodeNames + ", label="
				+ label + ", symbolSize=" + symbolSize + "]";
	}

	private Integer id; // 内部主键
	private Integer relationshipCount; // 关系数量
	private String nid; // 自定义主键?分别是用来？
	private String nodeNames; // 节点名称
	private String label; // 节点类别
	private String symbolSize; // 节点大小

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNid() {
		return nid;
	}

	public Integer getRelationshipCount() {
		return relationshipCount;
	}

	public void setRelationshipCount(Integer relationshipCount) {
		this.relationshipCount = relationshipCount;
	}

	public void setNid(String nid) {
		this.nid = nid;
	}

	public String getNodeNames() {
		return nodeNames;
	}

	public void setNodeNames(String nodeNames) {
		this.nodeNames = nodeNames;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getSymbolSize() {
		return symbolSize;
	}

	public void setSymbolSize(String symbolSize) {
		this.symbolSize = symbolSize;
	}
}
