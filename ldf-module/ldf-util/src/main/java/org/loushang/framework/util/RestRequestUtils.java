package org.loushang.framework.util;

import java.lang.reflect.Method;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

public class RestRequestUtils {
	
	private static Logger logger = LoggerFactory.getLogger(RestRequestUtils.class);
	
	private static RestTemplate restTemplate = new RestTemplate();

	/**
	 * 
	 * @param url[服务URl,支持占位符传参,例如：http://localhost:8080/demo/employees/{id}]
	 * @param responseType[返回值类型,例如：String.class]
	 * @param uriVariables[URl占位符参数,Map中的k值应于URl中占位符相对应]
	 * @return
	 */
	public static <T> T get(String url, Class<T> responseType, Map<String, ?> uriVariables) {
		url = adaptor(url);
		return restTemplate.getForObject(url, responseType, uriVariables);
	}
	
	/**
	 * 
	 * @param url[服务URl,例如：http://localhost:8080/demo/employees/]
	 * @param responseType[返回值类型,例如：String.class]
	 * @return
	 */
	public static <T> T get(String url, Class<T> responseType) {
		url = adaptor(url);
		return restTemplate.getForObject(url, responseType);
	}
	
	/**
	 * POST
	 * @param url[服务URl,例如：http://localhost:8080/demo/employees/]
	 * @param request[请求体，可以为null]
	 * @param responseType[返回值类型,例如：String.class]
	 * @return
	 */
	public static <T> T post(String url, Object request, Class<T> responseType) {
		url = adaptor(url);
		return restTemplate.postForObject(url, request, responseType);
	}

	/**
	 * PUT
	 * @param url[服务URl,支持占位符传参,例如：http://localhost:8080/demo/employees/{id}]
	 * @param request[请求体，可以为null]
	 * @param uriVariables[URl占位符参数,Map中的k值应于URl中占位符相对应]
	 */
	public static void put(String url, Object request, Map<String, ?> uriVariables) {
		url = adaptor(url);
		restTemplate.put(url, request, uriVariables);
	}
	
	/**
	 * PUT
	 * @param url[服务URl,例如：http://localhost:8080/demo/employees/]
	 * @param request[请求体，可以为null]
	 */
	public static void put(String url, Object request) {
		url = adaptor(url);
		restTemplate.put(url, request);
	}
	
	/**
	 * DELETE
	 * @param url[服务URl,支持占位符传参,例如：http://localhost:8080/demo/employees/{id}]
	 * @param uriVariables[URl占位符参数,Map中的k值应于URl中占位符相对应]
	 */
	public static void delete(String url, Map<String, ?> uriVariables) {
		url = adaptor(url);
		restTemplate.delete(url, uriVariables);
	}
	
	/**
	 * DELETE
	 * @param url[服务URl,例如：http://localhost:8080/demo/employees/]
	 */
	public static void delete(String url) {
		url = adaptor(url);
		restTemplate.delete(url);
	}
	/**
	 * Any
	 * @param url[服务URl,例如：http://localhost:8080/demo/employees/]
	 * @param method[请求方式，例如：HttpMethod.POST]
	 * @param responseType[返回值类型,例如：String.class]
	 * @param requestEntity[HttpEntity对象,支持设置请求header,与body]
	 * @return
	 */
	public static <T> T restRequest(String url,HttpMethod method, Class<T> responseType, HttpEntity<?> requestEntity,Map<String, ?> uriVariables){
		url = adaptor(url);
		return restTemplate.exchange(url, method, requestEntity, responseType, uriVariables).getBody();
	}
	/**
	 * Any
	 * @param url[服务URl,支持占位符传参,例如：http://localhost:8080/demo/employees/{id}]
	 * @param method[请求方式，例如：HttpMethod.POST]
	 * @param responseType[返回值类型,例如：String.class]
	 * @param requestEntity[HttpEntity对象,支持设置请求header,与body]
	 * @param uriVariables[URl占位符参数,Map中的k值应于URl中占位符相对应]
	 * @return
	 */
	public static <T> T restRequest(String url,HttpMethod method, Class<T> responseType, HttpEntity<?> requestEntity){
		url = adaptor(url);
		return restTemplate.exchange(url, method, requestEntity, responseType).getBody();
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
