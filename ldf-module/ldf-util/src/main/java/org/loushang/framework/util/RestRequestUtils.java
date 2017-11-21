package org.loushang.framework.util;

import java.lang.reflect.Method;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

public class RestRequestUtils {
	
	private static Logger logger = LoggerFactory.getLogger(RestRequestUtils.class);
	
	private static RestTemplate restTemplate = new RestTemplate();

	/**
	 * 
	 * @param url,ex:http://localhost:8080/demo/employees/{id}
	 * @param responseType:返回值类型,ex:String.class
	 * @param uriVariables:参数
	 * @return
	 */
	public static <T> T get(String url, Class<T> responseType, Map<String, ?> uriVariables) {
		url = adaptor(url);
		return restTemplate.getForObject(url, responseType, uriVariables);
	}
	
	/**
	 * 
	 * @param url,ex:http://localhost:8080/demo/employees
	 * @param responseType:返回值类型,ex:String.class
	 * @return
	 */
	public static <T> T get(String url, Class<T> responseType) {
		url = adaptor(url);
		return restTemplate.getForObject(url, responseType);
	}
	
	/**
	 * POST
	 * @param url
	 * @param request
	 * @param responseType
	 * @return
	 */
	public static <T> T post(String url, Object request, Class<T> responseType) {
		url = adaptor(url);
		return restTemplate.postForObject(url, request, responseType);
	}

	/**
	 * PUT
	 * @param url
	 * @param request
	 * @param uriVariables
	 */
	public static void put(String url, Object request, Map<String, ?> uriVariables) {
		url = adaptor(url);
		restTemplate.put(url, request, uriVariables);
	}
	
	/**
	 * PUT
	 * @param url
	 * @param request
	 */
	public static void put(String url, Object request) {
		url = adaptor(url);
		restTemplate.put(url, request);
	}
	
	/**
	 * DELETE
	 * @param url
	 * @param uriVariables
	 */
	public static void delete(String url, Map<String, ?> uriVariables) {
		url = adaptor(url);
		restTemplate.delete(url, uriVariables);
	}
	
	/**
	 * DELETE
	 * @param url
	 */
	public static void delete(String url) {
		url = adaptor(url);
		restTemplate.delete(url);
	}

	/**
	 * url适配
	 * @param url
	 * @return
	 */
	private static String adaptor(String url){
		try {
			Class<?> class1 = Class.forName("org.loushang.framework.util.RestRequestAdaptor");
			Method method = class1.getMethod("get", String.class);
			url = (String) method.invoke(class1, url);
		} catch (Exception e) {
			logger.error("org.loushang.framework.util.RestRequestAdaptor 类不存在,不进行适配");
		}
		return url;
	}
}
