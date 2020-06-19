package com.core.controller;

import com.core.service.BatchImportService;
import com.core.util.ResponseUtil;
import net.sf.json.JSONObject;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;


/** 上传下载文件 */
@Controller
@RequestMapping("/excel")
public class BatchImportController {

	@Resource
	private BatchImportService batchImportService;
	private static final Logger log = Logger.getLogger(BatchImportController.class);// 日志文件

	/**
	 * 导入
	 */
	@RequestMapping("/import")
	public String leadInExcelQuestionBank(@RequestParam("uploadExcel") CommonsMultipartFile uploadExcel, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String type = request.getParameter("type");
		JSONObject result = new JSONObject();

		String realPath = request.getServletContext().getRealPath("");
		String path = realPath + "/upload/";
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String fileName = dateFormat.format(new Date()) + uploadExcel.getOriginalFilename();
		Integer count = 0;
		InputStream in = null;
		InputStream in2 = null;

		long startTime = System.currentTimeMillis();
		long endTime1 = 0l;
		long endTime2 = 0l;

		try {
			// 获取前台exce的输入流
			in = uploadExcel.getInputStream();
			in2 = uploadExcel.getInputStream();
			// 将文件存入本地
			FileUtils.copyInputStreamToFile(in, new File(path, fileName));
			endTime1 = System.currentTimeMillis();
			// 删除过期文件
			batchImportService.deleteFile(path);
			// 写入文件日志
			batchImportService.uploadLog(path + "/导入日志/", fileName, type);
			// 处理数据
			count = batchImportService.importExcel(in2, type);
			endTime2 = System.currentTimeMillis();
			if (count > 0) {
				result.put("succ", true);
				result.put("message", "文件：" + uploadExcel.getOriginalFilename() + "导入成功！");
			} else {
				result.put("succ", false);
				result.put("message", "文件：" + uploadExcel.getOriginalFilename() + "导入失败！");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (in2 != null) {
					in2.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		log.info("上传文件成功！" + path);
		log.info(String.valueOf("文件上传耗时：" + (endTime1 - startTime) + "ms"));
		log.info(String.valueOf("插入数据库耗时：" + (endTime2 - endTime1) + "ms"));
		System.out.println("上传文件成功！" + path);
		System.out.println(String.valueOf("文件上传耗时：" + (endTime1 - startTime) + "ms"));
		System.out.println(String.valueOf("插入数据库耗时：" + (endTime2 - endTime1) + "ms"));
		ResponseUtil.write(response, result);
		return null;
	}

	/**
	 * 下载文件
	 */
	@RequestMapping("/download")
	public ResponseEntity<byte[]> download(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 取当前项目所在路径
		String realPath = request.getServletContext().getRealPath("");
		String path = realPath + "/download/导入模板.xls";
		File file = new File(path);
		HttpHeaders headers = new HttpHeaders();
		// 为了解决中文名称乱码问题
		String fileName = new String(file.getName().getBytes("UTF-8"), "iso-8859-1");
		headers.setContentDispositionFormData("attachment", fileName);
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		log.info("下载文件成功！" + path);
		return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), headers, HttpStatus.CREATED);
	}
}
