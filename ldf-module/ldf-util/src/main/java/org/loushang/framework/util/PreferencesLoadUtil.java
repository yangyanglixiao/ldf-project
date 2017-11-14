package org.loushang.framework.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class PreferencesLoadUtil {

	private static Log logger = LogFactory.getLog(PreferencesLoadUtil.class);

	public static final String PATH = "../settings/root.prefs";
	public static final String PATH_REAL = "WEB-INF/settings/root.prefs";

	protected static final char SEPARATOR = '/';

	private static String serviceFile = "serviceurl.conf";// 请求路径信息配置文件

	// 存放root.prefs里面的配置参数
	private static Map<String, String> preferences = null;
	// 存放serviceurl.conf里面的配置参数
	private static Map<String, String> urInfo = new HashMap<String, String>();

	private static ServletContext servletContext;
	private static String contextPath;

	public PreferencesLoadUtil(HttpServletRequest request) {
		HttpSession session = request.getSession();
		servletContext = session.getServletContext();
		contextPath = servletContext.getRealPath("/");
		load();
	}

	public PreferencesLoadUtil() {
		load();
	}

	private void load() {
		if (preferences == null) {
			preferences = new HashMap<String, String>();
			boolean flag = true;
			InputStream input = null;
			Properties result = new Properties();
			try {
				if (contextPath == null) {
					input = Thread.currentThread().getContextClassLoader().getResourceAsStream(PATH);
					if (input == null) {
						logger.error("加载文件[/WEB-INF/settings/root.prefs]失败，未配置该文件！");
						return;
					}
					result.load(input);
				} else {// 项目部署在linux的was上时，需要使用root.prefs文件的物理路径进行加载
					File propfile = new File(contextPath + PATH_REAL);
					if (!propfile.exists()) {
						logger.error("加载文件[/WEB-INF/settings/root.prefs]失败，未配置该文件！");
						return;
					}
					input = new FileInputStream(propfile);
					result.load(input);
				}

			} catch (FileNotFoundException e) {
				logger.error("加载文件[/WEB-INF/settings/root.prefs]失败，未配置该文件！");
				flag = false;
			} catch (IOException e) {
				logger.error(e);
				flag = false;
			} finally {
				if (input != null) {
					try {
						input.close();
					} catch (Exception e) {
						logger.error(e);
						flag = false;
					}
				}
			}
			if (flag) {
				// 读取key-value
				for (Iterator i = result.keySet().iterator(); i.hasNext();) {
					String fullKey = (String) i.next();
					String key = null;

					int lastIndex = fullKey.lastIndexOf(SEPARATOR);
					if (lastIndex == -1) {
						key = fullKey;
					} else {
						key = fullKey.substring(lastIndex + 1);
					}
					String value = result.getProperty(fullKey);
					if (value != null) {
						value = value.trim();
						preferences.put(key, value);
					}
				}
			}
		}
	}

	public Map<String, String> getPreferences() {
		return preferences;
	}

	/**
	 * 获取服务请求路径信息
	 * 
	 * @return
	 */
	public static Map<String, String> getServiceConf() {
		if (urInfo.isEmpty()) {
			loadServiceConf();
		}
		return urInfo;
	}

	/**
	 * 加载服务请求路径信息
	 */
	private static void loadServiceConf() {
		InputStream in = PreferencesLoadUtil.class.getClassLoader().getResourceAsStream(serviceFile);
		if (in == null) {
			in = Thread.currentThread().getContextClassLoader().getResourceAsStream(serviceFile);
			if (in == null) {
				return;
			}
		}
		if (logger.isDebugEnabled()) {
			logger.debug("Load serviceurl.conf...");
		}
		Properties result = new Properties();
		try {
			result.load(in);
			// 读取key-value
			Iterator it = result.keySet().iterator();
			while (it.hasNext()) {
				String key = (String) it.next();
				String val = (String) result.get(key);
				String[] keys = key.split("\\.");
				if (keys.length >= 2) {
					urInfo.put(keys[1], val);
				}
			}
		} catch (IOException e) {
			logger.error("加载serviceurl.conf出错：" + e.getMessage());
			e.printStackTrace();
		}
	}
}
