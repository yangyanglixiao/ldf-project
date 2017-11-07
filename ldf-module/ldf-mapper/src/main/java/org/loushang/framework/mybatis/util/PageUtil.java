package org.loushang.framework.mybatis.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * @title 页面工具类
 * @description:前端页面的通用处理方法
 * @author 框架产品组
 * 
 */
public class PageUtil {

	public static final String DEFAULT_FETCH_SIZE_KEY = "mybatis.fetchSize";

	private static Integer defaultFetchSize;

	private static Log log = LogFactory.getLog(PageUtil.class);

	public static final ThreadLocal<Object> total = new ThreadLocal<Object>();

	/**
	 * 将数据总量放入map
	 * 
	 * @param key
	 * @param count
	 */
	public static void setTotalCount(Object count) {
		total.set(count);
	}

	/**
	 * 取出当前页的数据总量
	 * 
	 * @param key
	 * @return
	 */
	public static int getTotalCount() {
		return getTotalCount("total");
	}

	public static int getTotalCount(Object key) {
		int count = -1;
		try {
			if (total.get() == null) {
				log.debug("未开启获取查询记录总条数的配置。");
			} else {
				count = (Integer) total.get();
			}
		} catch (NumberFormatException e) {
			log.error(e);
		}
		total.remove();
		return count;
	}

	// mybatis的默认取出行数
	public static Integer getDefaultFetchSize() {
		if (defaultFetchSize == null) {
			// try {
			// Properties properties=new Properties();
			// System.out.println(properties);
			// InputStream resourceAsStream =
			// PageUtil.class.getResourceAsStream("global.properties");
			// properties.load(resourceAsStream);
			// String fetchSizeValue = properties.getProperty(DEFAULT_FETCH_SIZE_KEY);
			// defaultFetchSize = Integer.parseInt(fetchSizeValue);
			// } catch (Exception e) {
			// log.error(e);
			// defaultFetchSize=new Integer(100000);
			// }
			defaultFetchSize = new Integer(100000);
		}

		return defaultFetchSize;
	}
}
