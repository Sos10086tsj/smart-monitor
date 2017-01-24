package com.chinesedreamer.smartmonitor.util;

import java.util.Random;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chinesedreamer.smartmonitor.GlobalResouce;
import com.chinesedreamer.smartmonitor.tools.mail.model.MailConfig;

/**
 * Description:
 * Auth:Paris
 * Date:Jan 23, 2017
**/
public class MailUtil {
	
	/**
	 * 初始化邮件配置加载
	 */
	public static void initMailConfigLoading(){
		JSONObject jsonObject = FileUtil.getJsonFromFile(FileUtil.getResouceRootPath("json/config.json"));
		JSONObject root = jsonObject.getJSONObject("config");
		JSONArray mailConfigs = root.getJSONArray("mail");
		GlobalResouce.mailConfigs.clear();
		
		PropertiesUtil pu = new PropertiesUtil("application.properties");
		String env = pu.getProperty("env.mail.prefix");
		for (Object object : mailConfigs) {
			JSONObject mailConfig = (JSONObject)object;
			MailConfig mc = JSON.toJavaObject(mailConfig,MailConfig.class);
			mc.setEnv(env);
			GlobalResouce.mailConfigs.add(mc);
		}
	}
	
	public static MailConfig getConfig() {
		int size = GlobalResouce.mailConfigs.size();
		if (size == 0) {
			initMailConfigLoading();
		}
		Random random = new Random();
		int index = random.nextInt(size);
		return GlobalResouce.mailConfigs.get(index);
	}
}
