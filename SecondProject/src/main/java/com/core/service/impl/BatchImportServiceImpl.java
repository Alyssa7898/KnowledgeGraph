package com.core.service.impl;

import com.core.service.BatchImportService;
import com.core.service.impl.ExcelToMap;
import com.core.util.JdbcUtil;
import com.core.util.MD5Util;
import jxl.Workbook;
import org.springframework.stereotype.Service;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

@Service("BatchImportService")
public class BatchImportServiceImpl implements BatchImportService {

	/** 根据文件名时间，删除半年前历史数据 */
	@Override
	public void deleteFile(String path) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.MONTH, -6);

		File folder = new File(path);
		if (!folder.exists()) {
			folder.mkdir();
		} else {
			File[] files = folder.listFiles();
			for (int i = 0; i < files.length; i++) {
				File file = files[i];
				if (!file.isDirectory()) {
					try {
						// 匹配文件名删除半年前的数据
						if (dateFormat.parse(file.getName().substring(0, 8)).before(calendar.getTime())) {
							file.delete();
						}
					} catch (Exception e) {
						// 移动异常文件到新文件夹
						File folder2 = new File(path + "/异常文件/");
						if (!folder2.exists()) {
							folder2.mkdir();
						}
						copy(file, new File(path + "/异常文件/" + file.getName()));
						file.delete();
						e.printStackTrace();
					}
				}
			}
		}
	}

	/** 写出导入日志文件 */
	@Override
	public void uploadLog(String path, String fileName, String type) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String lineSeparator = System.getProperty("line.separator", "/n");
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append("导入时间：");
		stringBuffer.append(dateFormat.format(new Date()));
		stringBuffer.append("\t文件名：");
		stringBuffer.append(fileName);
		stringBuffer.append("\t导入类型：");
		stringBuffer.append("1".equals(type) ? "批量导入" : "增量导入");
		stringBuffer.append(lineSeparator);
		// 判断文件夹是否存在
		File folder = new File(path);
		if (!folder.exists()) {
			folder.mkdir();
		}

		// 判断文件是否存在
		File folder2 = new File(path + "/导入日志.txt");
		if (!folder2.exists()) {
			try {
				folder2.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		// 追加写入记录
		try {
			FileWriter writer = new FileWriter(folder2, true);
			writer.write(stringBuffer.toString());
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/** 导入数据 */
	@Override
	public Integer importExcel(InputStream in, String type) {
		int ret = 0;
		Workbook wb = null;
		try {
			wb = Workbook.getWorkbook(in);
			// 批量导入 删除数据
			if ("1".equals(type)) {
				// 删除所有数据，保留User数据
				deleteAll();
				// 创建所有和admin用户
				createIndex();
			}
			// 初始化

			ExcelToMap.init(wb);
			// 创建节点
			ExcelToMap.writeNode();
			// 创建节点和关系
			ExcelToMap.writeLine(wb);
			ret = 1;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (wb != null) {
					wb.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return ret;
	}

	/** 文件拷贝方法 */
	public void copy(File from, File to) {
		try {
			InputStream in = new FileInputStream(from);
			OutputStream out = new FileOutputStream(to);
			byte[] buff = new byte[1024];
			int len = 0;
			while ((len = in.read(buff)) != -1) {
				out.write(buff, 0, len);
			}
			in.close();
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/** 创建索引和管理员账户 */
	private void createIndex() {
		JdbcUtil jdbcUtil = new JdbcUtil();
		jdbcUtil.getConnection();
		try {
			// 创建索引
			jdbcUtil.update("CREATE INDEX ON :Chapter(name)");
			jdbcUtil.update("CREATE INDEX ON :Successor(name)");
			jdbcUtil.update("CREATE INDEX ON :Subjects(name)");
			jdbcUtil.update("CREATE INDEX ON :Topic(name)");
			jdbcUtil.update("CREATE INDEX ON :User(name)");
			jdbcUtil.update("CREATE INDEX ON :User(username)");
			/*跟清空数据库之后的操作重复*/
			List<Map<String, Object>> findList = jdbcUtil.findList("MATCH (na:User) where na.username='admin' return na");
			if (findList.size() <= 0 || null == findList) {
				jdbcUtil.update("CREATE (na:User{username:'admin',name:'管理员',role:'1',password:{1}}) ",
						MD5Util.MD5Encode("admin", "UTF-8"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != jdbcUtil) {
				jdbcUtil.close();
			}
		}
	}

	/** 清空数据库 */
	private void deleteAll() {
		JdbcUtil jdbcUtil = new JdbcUtil();
		jdbcUtil.getConnection();
		try {
			List<Map<String, Object>> findList = jdbcUtil.findList("MATCH (na:User) return na");
			// 清空数据库
			jdbcUtil.update("MATCH (n) DETACH DELETE n ");
			//创建User
			for (Map<String, Object> map : findList) {
				@SuppressWarnings("unchecked")
				HashMap<String, String> map2 = (HashMap<String, String>) map.get("na");
				jdbcUtil.update("create (na:User{username:{1}, password:{2}, name:{3}, phone:{4}, email:{5}, role:{6}}) return na",
						map2.get("username"), map2.get("password"), map2.get("name"), map2.get("phone"), map2.get("email"),
						map2.get("role"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != jdbcUtil) {
				jdbcUtil.close();
			}
		}
	}
}
