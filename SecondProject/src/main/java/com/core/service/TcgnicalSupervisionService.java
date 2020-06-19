package com.core.service;

import net.sf.json.JSONObject;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.util.ArrayList;

public interface TcgnicalSupervisionService extends Serializable {
	/** 查询 */
	public JSONObject find(ArrayList<String> list);

	/** 查询页面下拉框内容 */
	public JSONObject findLables();
	/*通过知识点的id获得对应章节*/
	public String getChapterByTopic(int id);

}
