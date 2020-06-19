package com.core.util;

import java.util.ArrayList;

public class LabelsUtil {
	public static ArrayList<String> lineList = new ArrayList<String>();

	static {
		// 关系列表
		lineList.add("TOPICCHAPTER");
		lineList.add("TOPICSUCCESSOR");
		lineList.add("TOPICSUBJECT");
		lineList.add("CHAPTERSUCCESSOR");
		lineList.add("CHAPTERSUBJECT");
		lineList.add("SUCCESSORSUBJECT");
	}

	/** 转换为汉字 */
	public static String toChinese(String _label) {
		if ("Chapter".equals(_label) || "章节".equals(_label)) {
			_label = "章节";
		} else if ("Successor".equals(_label) || "后续章节".equals(_label)) {
			_label = "后续章节";
		} else if ("Subject".equals(_label) || "科目".equals(_label)) {
			_label = "科目";
		} else if ("Topic".equals(_label) || "知识点".equals(_label)) {
			_label = "知识点";
		} else {
			return "";
		}
		return _label;
	}

	/** 转换为字母 */
	public static String toEnglish(String _label) {
		if ("Chapter".equals(_label) || "章节".equals(_label)) {
			_label = "Chapter";
		} else if ("Successor".equals(_label) || "后续章节".equals(_label)) {
			_label = "Successor";
		} else if ("Subject".equals(_label) || "科目".equals(_label)) {
			_label = "Subject";
		} else if ("Topic".equals(_label) || "知识点".equals(_label)) {
			_label = "Topic";
		} else {
			return "";
		}
		return _label;
	}

	/** 将两个类型标签转换为关系 */
	public static String labelToLine(String _label1, String _label2) {
		for (String ls : lineList) {
			if (ls.contains(toEnglish(_label1).toUpperCase()) && ls.contains(toEnglish(_label2).toUpperCase())) {
				return ls;
			}
		}
		return "";
	}
}
