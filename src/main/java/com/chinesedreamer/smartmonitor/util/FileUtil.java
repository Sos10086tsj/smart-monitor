package com.chinesedreamer.smartmonitor.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * Description: Auth:Paris Date:Mar 23, 2016
 **/
public class FileUtil {
	
	private static Logger logger = LoggerFactory.getLogger(FileUtil.class);

	public static byte[] readInputStream2ByteArray(InputStream is){
		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024 * 4];
			int n = 0;
			while ((n = is.read(buffer)) != -1) {
				out.write(buffer, 0, n);
			}
			byte[] result = out.toByteArray();
			out.close();
			return result;
		} catch (Exception e) {
			logger.error("{}",e);
			return null;
		}
		
	}

	public static String getResouceRootPath(String fileName) {
		return FileUtil.class.getClassLoader().getResource(fileName).getPath();
	}

	/**
	 * 读取文件字符串
	 * 
	 * @param path
	 * @return
	 * @throws Exception
	 */
	public static String readFile2Json(String path){
		StringBuffer buffer = new StringBuffer();
		try {
			FileInputStream fis = new FileInputStream(path);
			InputStreamReader isReader = new InputStreamReader(fis, "UTF-8");
			BufferedReader reader = new BufferedReader(isReader);
			String tmpStr = null;
			while (null != (tmpStr = reader.readLine())) {
				buffer.append(tmpStr);
			}
			reader.close();
		} catch (Exception e) {
			logger.error("{}",e);
		}
		return buffer.toString();
	}

	/**
	 * 解析json文件
	 * 
	 * @param path
	 * @return
	 * @throws Exception
	 */
	public static JSONObject getJsonFromFile(String path){
		String content = readFile2Json(path);
		return JSONObject.parseObject(content);
	}

	/**
	 * 解析json文件
	 * 
	 * @param path
	 * @return
	 * @throws Exception
	 */
	public static JSONArray getJsonArrayFromFile(String path){
		String content = readFile2Json(path);
		return JSONArray.parseArray(content);
	}
}
