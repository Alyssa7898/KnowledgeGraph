package com.core.service.impl;

import com.core.util.JdbcUtil;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Set;

/**
 * 读取流,将数据转成Map,插入数据库
 */
public class ExcelToMap {
	public static HashMap<String, Integer> chapterMap = new HashMap<String, Integer>();
	public static HashMap<String, Integer> successorMap = new HashMap<String, Integer>();
	public static HashMap<String, Integer> subjectMap = new HashMap<String, Integer>();
	public static HashMap<String, Integer> topicMap = new HashMap<String, Integer>();

	/** 将Excel数据转换为Map集合 */
	public static void init(Workbook wb) throws IOException, BiffException {
		int ortherId = 10000;
		// int sheet_size = wb.getNumberOfSheets();
		for (int index = 0; index < 1; index++) {
			// 每个页签创建一个Sheet对象
			Sheet sheet = wb.getSheet(index);
			// sheet.getRows()返回该页的总行数
			for (int i = 1; i < sheet.getRows(); i++) {
				for (int j = 0; j < sheet.getColumns(); j++) {
					// 过滤特殊字符
					String cellinfo = sheet.getCell(j, i).getContents().replace("\t", " ").replace("\r", " ").replace("\n", " ")
							.replace("\\", "/").trim();
					// 过滤特殊字符后过滤空格
					String cellinfo2 = cellinfo.replace(" ", "").replace("　", "");

					if (j == 0) {// 章节

						if (!"".equals(cellinfo2) && null != cellinfo2 && !"NA".equals(cellinfo2)) {
							if (chapterMap.get(cellinfo2) == null) {
								chapterMap.put(cellinfo2, ++ortherId);
							}
						} else {
							if (chapterMap.get("空章节") == null) {
								chapterMap.put("空章节", ++ortherId);
							}
						}
					}
					if (j == 1) {// 后续章节
						if (!"".equals(cellinfo2) && null != cellinfo2 && !"NA".equals(cellinfo2)) {
							if (successorMap.get(cellinfo2) == null) {
								successorMap.put(cellinfo2, ++ortherId);
							}
						} else {
							if (successorMap.get("空后续章节") == null) {
								successorMap.put("空后续章节", ++ortherId);
							}
						}
					}
					if (j == 2) {// 科目
						if (!"".equals(cellinfo2) && null != cellinfo2 && !"NA".equals(cellinfo2)) {
							if (subjectMap.get(cellinfo) == null) {
								subjectMap.put(cellinfo, ++ortherId);
							}
						} else {
							if (subjectMap.get("空科目") == null) {
								subjectMap.put("空科目", ++ortherId);
							}
						}
					}
					if (j == 3) {// 知识点
						if (topicMap.get(cellinfo) == null) {
							topicMap.put(cellinfo, ++ortherId);
						}
					}
				}
			}
		}
	}

	/** 创建节点 */
	public static void writeNode() throws SQLException {
		String line = "";
		JdbcUtil jdbcUtil = new JdbcUtil();
		jdbcUtil.getConnection();
		// 章节
		for (Object obj : chapterMap.keySet()) {
			line = "MERGE (:Chapter {name:'" + obj.toString() + "'})";
			jdbcUtil.update(line);
		}

		// 后续章节
		for (Object obj : successorMap.keySet()) {
			line = "MERGE (:Successor {name:'" + obj.toString() + "'})";
			jdbcUtil.update(line);
		}

		// 科目
		for (Object obj : subjectMap.keySet()) {
			line = "MERGE (:Subject {name:'" + obj.toString() + "'})";
			jdbcUtil.update(line);
		}


		// 知识点
		for (Object obj : topicMap.keySet()) {
			line = "MERGE (:Topic {name:'" + obj.toString() + "'})";
			jdbcUtil.update(line);
		}
	}

	/** 创建关系
	 * 修改的这里 */
	public static void writeLine(Workbook wb) throws Exception {
		String pre="";
		HashMap<String, String> map = new HashMap<String, String>();
		// int sheet_size = wb.getNumberOfSheets();
		for (int index = 0; index < 1; index++) {
			Sheet sheet = wb.getSheet(index);
			String firstChapter = sheet.getCell(0,1).getContents().replace("\t", " ").replace("\r", " ").replace("\n", " ")
					.replace("\\", "/").trim();
			String firstChapter2=firstChapter.replace(" ", "").replace("　", "");
			for (int i = 1; i < sheet.getRows(); i++) {//行
				String chapter = "";
				String successor = "";
				String subject = "";
				String topic = "";
				String chapter_on = "";
				for (int j = 0; j < sheet.getColumns(); j++) {//列
					String cellinfo = sheet.getCell(j, i).getContents().replace("\t", " ").replace("\r", " ").replace("\n", " ")
							.replace("\\", "/").trim();
					String cellinfo2 = cellinfo.replace(" ", "").replace("　", "");
					if (j == 0) {// 章节
						if (!"".equals(cellinfo2) && null != cellinfo2 && !"NA".equals(cellinfo2)) {
							chapter = cellinfo2;
						} else {
							chapter = "空章节";
						}
					} else if (j == 1) {// 后续章节
						if (!"".equals(cellinfo2) && null != cellinfo2 && !"NA".equals(cellinfo2)) {
							successor = cellinfo2;
							chapter_on = cellinfo2;
						} else {
							successor = "空后续章节";
							chapter_on = firstChapter2;
						}
					} else if (j == 2) {// 科目
						if (!"".equals(cellinfo2) && null != cellinfo2 && !"NA".equals(cellinfo2)) {
							subject = cellinfo2;
						} else {
							subject = "空科目";
						}
					} else if (j == 3) {// 知识点
						topic = cellinfo;
					}
				}




				System.out.println(chapter);
				System.out.println(chapter_on);
				if (!pre.equals(chapter))
					/*System.out.println("插入");*/
					map.put("MATCH (aa:Chapter {name:'" + chapter + "'}), (bb:Chapter {name:'" + chapter_on
							+ "'}) MERGE (aa) -[:CARRYON{name:''}]-> (bb)", "");
				pre=chapter;
				// 知识点—>科目
				map.put("MATCH (aa:Topic {name:'" + topic + "'}), (bb:Subject {name:'" + subject
						+ "'}) MERGE (aa) -[:TOPICSUBJECT{name:''}]-> (bb)", "");

				// 知识点—>章节22
				map.put("MATCH (aa:Topic {name:'" + topic + "'}), (bb:Chapter {name:'" + chapter
						+ "'}) MERGE (aa) -[:TOPICCHAPTER{name:''}]-> (bb)", "");
				// --------------------------------------------------------------------------

				// 章节—后续章节
				map.put("MATCH (aa:Chapter {name:'" + chapter + "'}), (bb:Successor {name:'" + successor
						+ "'}) MERGE (aa) -[:CHAPTERSUCCESSOR{name:''}]-> (bb)", "");



				// 章节—科目22
				map.put("MATCH (aa:Chapter {name:'" + chapter + "'}), (bb:Subject {name:'" + subject
						+ "'}) MERGE (aa) -[:CHAPTERSUBJECT{name:''}]-> (bb)", "");

				// --------------------------------------------------------------------------



			}
		}
		JdbcUtil jdbcUtil = new JdbcUtil();
		jdbcUtil.getConnection();

		Set<String> set = map.keySet();
		for (String string : set) {
			try {
				jdbcUtil.update(string);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {//测试来着

		InputStream is = null;
		Workbook wb = null;
		try {
			is = new FileInputStream(new File("C:/Users/LZ/Desktop/关键词筛过的数据关联设备0504 - 副本.xls").getAbsolutePath());
			wb = Workbook.getWorkbook(is);
			init(wb);
			System.out.println();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				wb.close();
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
