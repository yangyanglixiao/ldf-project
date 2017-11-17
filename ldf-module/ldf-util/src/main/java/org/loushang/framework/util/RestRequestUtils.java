package org.loushang.framework.util;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import org.springframework.web.client.RestTemplate;

public class RestRequestUtils {

	private static RestTemplate restTemplate = new RestTemplate();

	/**
	 * 
	 * @param url,ex:http://localhost:8080/demo/employees/{id}
	 * @param responseType:返回值类型,ex:String.class
	 * @param uriVariables:参数
	 * @return
	 */
	public static <T> T getForObject(String url, Class<T> responseType, Map<String, ?> uriVariables) {
		url = isK8s(url);
		return restTemplate.getForObject(url, responseType, uriVariables);
	}
	/**
	 * 
	 * @param url,ex:http://localhost:8080/demo/employees
	 * @param responseType:返回值类型,ex:String.class
	 * @return
	 */
	public static <T> T getForObject(String url, Class<T> responseType) {
		url = isK8s(url);
		return restTemplate.getForObject(url, responseType);
	}
	/**
	 * POST
	 * @param url
	 * @param request
	 * @param responseType
	 * @return
	 */
	public static <T> T postForObject(String url, Object request, Class<T> responseType) {
		url = isK8s(url);
		return restTemplate.postForObject(url, request, responseType);
	}

	/**
	 * PUT
	 * @param url
	 * @param request
	 * @param uriVariables
	 */
	public static void put(String url, Object request, Map<String, ?> uriVariables) {
		url = isK8s(url);
		restTemplate.put(url, request, uriVariables);
	}
	/**
	 * PUT
	 * @param url
	 * @param request
	 */
	public static void put(String url, Object request) {
		url = isK8s(url);
		restTemplate.put(url, request);
	}
	/**
	 * DELETE
	 * @param url
	 * @param uriVariables
	 */
	public static void delete(String url, Map<String, ?> uriVariables) {
		url = isK8s(url);
		restTemplate.delete(url, uriVariables);
	}
	/**
	 * DELETE
	 * @param url
	 */
	public static void delete(String url) {
		url = isK8s(url);
		restTemplate.delete(url);
	}
	/**
	 * url适配
	 * @param url
	 * @return
	 */
	private static String isK8s(String urlStr){
		if(urlStr!=null&&!"".equals(urlStr)){
			try {
				URL url = new URL(urlStr);
				String Protocol = url.getProtocol();
				String nameService = url.getHost();
				String path = url.getPath();
				if(nameService!=null&&!"".equals(nameService)){
					String service_host = System.getenv(nameService.toUpperCase()+"_SERVICE_HOST");
					String service_port = System.getenv(nameService.toUpperCase()+"_SERVICE_PORT");
					if(null!=service_host&&!"".equals(service_host)&&null!=service_port&&!"".equals(service_port)){
						urlStr = Protocol+ "://" + service_host + ":" + service_port + "/" +nameService + path;
					}
				}
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		}
		return urlStr;
		
	}
}
