package com.core.entity;

import java.io.Serializable;

/** 图谱展示实体类 */
public class TcgnicalSupervision implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id;// 内部id?
	private String labels;// 节点类型
	private String type;// 边类型
	private String startId;// 边起点
	private String endId;// 边终点
	private String nid;// 自定义id?
	private String name;// 节点名
	private String value;// 节点值
	private String symbolSize;// 节点大小

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLabels() {
		return labels;
	}

	public void setLabels(String labels) {
		this.labels = labels;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getStartId() {
		return startId;
	}

	public void setStartId(String startId) {
		this.startId = startId;
	}

	public String getEndId() {
		return endId;
	}

	public void setEndId(String endId) {
		this.endId = endId;
	}

	public String getNid() {
		return nid;
	}

	public void setNid(String nid) {
		this.nid = nid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getSymbolSize() {
		return symbolSize;
	}

	public void setSymbolSize(String symbolSize) {
		this.symbolSize = symbolSize;
	}
}
