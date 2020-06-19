package com.core.service;

import java.io.InputStream;

public interface BatchImportService {
	/** 根据文件名时间，删除半年前历史数据 */
	public void deleteFile(String path);

	/** 写出导入日志文件 */
	public void uploadLog(String path, String fileName, String type);

	/** 导入数据 */
	public Integer importExcel(InputStream in2, String type);

}
