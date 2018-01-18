package org.loushang.framework.util;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

public class PropertiesUtil {
	private static String configFile = "conf.properties";
	//缓存容器
	private static Map<String,String> cachedMap = new HashMap<String,String>();

	/********
	 * 加载classpath下properties文件
	 * @param properties 文件名称
	 * @return
	 */
	public static Properties getProperties(String properties) {
		try {
			InputStream in = PropertiesUtil.class.getClassLoader()
					.getResourceAsStream(properties);
			Properties p = new Properties();
			p.load(in);
			return p;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	/******
	 * 获取配置文件中指定的属性值
	 * @param properties 文件名称
	 * @param key  键值
	 * @return
	 */
	public static String getValue(String properties,String key){
		//1.加载配置文件并缓存
		Properties p = getProperties(properties);
		Iterator it = p.keySet().iterator();
		while (it.hasNext()) {
			String k = (String) it.next();
			cachedMap.put(k, (String) p.get(k));
		}
		//2.如果没有获取到，读取环境变量
		if(cachedMap.get(key)==null){
			String v = System.getenv(key);
			cachedMap.put(key, v);
		}
		
		return cachedMap.get(key);
	}
	/****
	 * 获取指定配置变量的值
	 * @param key
	 * @return
	 */
	public static String getValue(String key){
		
		String value = cachedMap.get(key);
		
		if(value==null){
			value = getValue(configFile,key);
		}
		
		return value;
	}
	/****
	 * 获取指定的变量值
	 * @param keys
	 * @return
	 */
	public static Map<String,String> getValues(String[] keys){
		Map<String,String>  values = new HashMap<String, String>();
		
		for(int i=0; i < keys.length; ++i){
			String value = getValue(keys[i]);
			values.put(keys[i], value);
		}
		
		return values;
	}
	/****
	 * 获取所有配置信息。
	 * 如果为空，加载conf.properties中的配置。
	 * @return
	 */
	public static Map<String,String> getAllValues(){
		if(cachedMap.isEmpty()){
			//加载配置文件并缓存
			Properties p = getProperties(configFile);
			Iterator it = p.keySet().iterator();
			while (it.hasNext()) {
				String k = (String) it.next();
				cachedMap.put(k, (String) p.get(k));
			}
		}
		return cachedMap;
	}

}
