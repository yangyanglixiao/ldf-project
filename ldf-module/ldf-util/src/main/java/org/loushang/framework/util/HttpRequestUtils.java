package org.loushang.framework.util;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @title Rest访问工具
 * @author 框架产品组
 *
 */
public class HttpRequestUtils {
    
    private static Logger logger = LoggerFactory.getLogger(HttpRequestUtils.class); // 日志记录

    /**
     * httpPost
     * 
     * @param url[路径]
     * @param jsonParam[参数]
     * @return
     */
    public static JSONObject httpPost(String url, JSONObject jsonParam) {
        return httpPost(url, jsonParam, false);
    }

    /**
     * post请求
     * 
     * @param url[url地址]
     * @param jsonParam[参数]
     * @param noNeedResponse[不需要返回结果]
     * @return
     */
    public static JSONObject httpPost(String url, JSONObject jsonParam, boolean noNeedResponse) {
        // post请求返回结果
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        JSONObject jsonResult = null;
        HttpPost method = new HttpPost(url);
        try {
            if (null != jsonParam) {
                StringEntity entity = new StringEntity(jsonParam.toString(), "utf-8");
                entity.setContentEncoding("UTF-8");
                entity.setContentType("application/json");
                method.setEntity(entity);
            }
            HttpResponse result = httpClient.execute(method);
            url = URLDecoder.decode(url, "UTF-8");
            // 请求发送成功，并得到响应 
            if (result.getStatusLine().getStatusCode() == 200) {
                String str = "";
                try {
                    // 读取服务器返回过来的json字符串数据
                    str = EntityUtils.toString(result.getEntity());
                    if (noNeedResponse) {
                        return null;
                    }
                    // 把json字符串转换成json对象
                    jsonResult = JSONObject.fromObject(str);
                } catch (Exception e) {
					logger.error("post请求提交失败:" + url, e);
                }
            }
        } catch (IOException e) {
			logger.error("post请求提交失败:" + url, e);
        } finally {
            try {
                httpClient.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return jsonResult;
    }


    /**
     * 发送get请求
     * 
     * @param url[路径]
     * @return
     */
    public static JSONObject httpGet(String url) {
        // get请求返回结果
        JSONObject jsonResult = null;
        try {
            CloseableHttpClient httpClient = HttpClientBuilder.create().build();
            // 发送get请求
            HttpGet request = new HttpGet(url);
            HttpResponse response = httpClient.execute(request);

            // 请求发送成功，并得到响应
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                // 读取服务器返回过来的json字符串数据
                String strResult = EntityUtils.toString(response.getEntity());
                // 把json字符串转换成json对象
                jsonResult = JSONObject.fromObject(strResult);
                url = URLDecoder.decode(url, "UTF-8");
            } else {
				logger.error("get请求提交失败:" + url);
            }
        } catch (IOException e) {
			logger.error("get请求提交失败:" + url, e);
        }
        return jsonResult;
    }
    
    /**
     * 发送get请求
     * 
     * @param url[路径]
     * @return
     */
    public static String get(String url) {
        String strResult = null;
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        try {
            // 发送get请求
            HttpGet request = new HttpGet(url);
            HttpResponse response = httpClient.execute(request);

            // 请求发送成功，并得到响应
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                // 读取服务器返回过来的json字符串数据
                strResult = EntityUtils.toString(response.getEntity());
                url = URLDecoder.decode(url, "UTF-8");
            } else {
                logger.error("get请求提交失败:" + url);
            }
        } catch (IOException e) {
            logger.error("get请求提交失败:" + url, e);
        } finally {
            try {
                httpClient.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return strResult;
    }
    
    /**
     * post请求
     * 
     * @param url[url地址]
     * @param jsonParam[参数]
     * @param noNeedResponse[不需要返回结果]
     * @return
     */
    public static String post(String url, JSONObject jsonParam) {
        // post请求返回结果
        String strResult = null;
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost method = new HttpPost(url);
        try {
            if (null != jsonParam) {
                StringEntity entity = new StringEntity(jsonParam.toString(), "utf-8");
                entity.setContentEncoding("UTF-8");
                entity.setContentType("application/json");
                method.setEntity(entity);
            }
            HttpResponse result = httpClient.execute(method);
            url = URLDecoder.decode(url, "UTF-8");
            // 请求发送成功，并得到响应 
            if (result.getStatusLine().getStatusCode() == 200) {
                try {
                    // 读取服务器返回过来的json字符串数据
                    strResult = EntityUtils.toString(result.getEntity());
                } catch (Exception e) {
                    logger.error("post请求提交失败:" + url, e);
                }
            }
        } catch (IOException e) {
            logger.error("post请求提交失败:" + url, e);
        } finally {
            try {
                httpClient.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return strResult;
    }
    
    /**
     * 发送get请求
     * 
     * @param url[路径]
     * @return
     */
    public static JSONArray httpGetWithJSONArray(String url) {
        // get请求返回结果
        JSONArray jsonResult = null;
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        try {
            // 发送get请求
            HttpGet request = new HttpGet(url);
            HttpResponse response = httpClient.execute(request);

            // 请求发送成功，并得到响应
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                // 读取服务器返回过来的json字符串数据
                String strResult = EntityUtils.toString(response.getEntity());
                // 把json字符串转换成json对象
                jsonResult = JSONArray.fromObject(strResult);
                url = URLDecoder.decode(url, "UTF-8");
            } else {
                logger.error("get请求提交失败:" + url);
            }
        } catch (IOException e) {
            logger.error("get请求提交失败:" + url, e);
        } finally {
            try {
                httpClient.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return jsonResult;
    }
    
    /**
     * post请求
     * 
     * @param url[url地址]
     * @param jsonParam[参数]
     * @param noNeedResponse[不需要返回结果]
     * @return
     */
    public static JSONArray httpPostWithJSONArray(String url, JSONObject jsonParam) {
        // post请求返回结果
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        JSONArray jsonResult = null;
        HttpPost method = new HttpPost(url);
        try {
            if (null != jsonParam) {
                StringEntity entity = new StringEntity(jsonParam.toString(), "utf-8");
                entity.setContentEncoding("UTF-8");
                entity.setContentType("application/json");
                method.setEntity(entity);
            }
            HttpResponse result = httpClient.execute(method);
            url = URLDecoder.decode(url, "UTF-8");
            // 请求发送成功，并得到响应 
            if (result.getStatusLine().getStatusCode() == 200) {
                String str = "";
                try {
                    // 读取服务器返回过来的json字符串数据
                    str = EntityUtils.toString(result.getEntity());
                    // 把json字符串转换成json对象
                    jsonResult = JSONArray.fromObject(str);
                } catch (Exception e) {
                    logger.error("post请求提交失败:" + url, e);
                }
            }
        } catch (IOException e) {
            logger.error("post请求提交失败:" + url, e);
        } finally {
            try {
                httpClient.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return jsonResult;
    }
    
    /**
     * post请求
     * 
     * @param url[url地址]
     * @param jsonParam[参数]
     * @param noNeedResponse[不需要返回结果]
     * @return
     */
    public static JSONArray httpPostWithJSONArray(String url, JSONArray jsonArray) {
        // post请求返回结果
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        JSONArray jsonResult = null;
        HttpPost method = new HttpPost(url);
        try {
            if (null != jsonArray) {
                StringEntity entity = new StringEntity(jsonArray.toString(), "utf-8");
                entity.setContentEncoding("UTF-8");
                entity.setContentType("application/json");
                method.setEntity(entity);
            }
            HttpResponse result = httpClient.execute(method);
            url = URLDecoder.decode(url, "UTF-8");
            // 请求发送成功，并得到响应 
            if (result.getStatusLine().getStatusCode() == 200) {
                String str = "";
                try {
                    // 读取服务器返回过来的json字符串数据
                    str = EntityUtils.toString(result.getEntity());
                    // 把json字符串转换成json对象
                    jsonResult = JSONArray.fromObject(str);
                } catch (Exception e) {
                    logger.error("post请求提交失败:" + url, e);
                }
            }
        } catch (IOException e) {
            logger.error("post请求提交失败:" + url, e);
        } finally {
            try {
                httpClient.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return jsonResult;
    }
    
    /**
     * 发送get请求,返回Map类型数据
     * @param url[请求URL]
     * @return
     */
    public static Map<String, Object> httpGetWithMap(String url) {
        HashMap<String, Object> data = new HashMap<String, Object>();
        JSONObject jsonObject = httpGet(url);
        if(jsonObject!=null){
            Iterator it = jsonObject.keys();
            // 遍历jsonObject数据，添加到Map对象
            while (it.hasNext()) {
                String key = String.valueOf(it.next());
                data.put(key, jsonObject.get(key));
            }
            return data;
        }
        return null;
    }
    
    /**
     * post请求，返回Map类型数据
     * @param url
     * @param jsonParam
     * @param noNeedResponse
     * @return
     */
    public static Map<String, Object> httpPostWithMap(String url, JSONObject jsonParam){
        HashMap<String, Object> data = new HashMap<String, Object>();
        JSONObject jsonObject = httpPost(url, jsonParam);
        if(jsonObject!=null){
            Iterator it = jsonObject.keys();
            // 遍历jsonObject数据，添加到Map对象
            while (it.hasNext()) {
                String key = String.valueOf(it.next());
                data.put(key, jsonObject.get(key));
            }
            return data;
        }
        return null;
    }
    
    /**
     * 发送get请求,返回List类型数据
     * @param url[请求URL]
     * @return
     */
    public static List<Map<String, Object>> httpGetWithList(String url) {
        JSONArray jsonArray = httpGetWithJSONArray(url);
        if(jsonArray!=null){
            List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
            HashMap<String, Object> map = null;
            for(int i=0;i<jsonArray.size();i++){
                map = new HashMap<String, Object>();
                JSONObject jObject = (JSONObject)jsonArray.get(i);
                Iterator it = jObject.keys();
                // 遍历jsonObject数据，添加到Map对象
                while (it.hasNext()) {
                    String key = String.valueOf(it.next());
                    map.put(key, jObject.get(key));
                }
                data.add(map);
            }
            return data;
        }
        return null;
    }
    
    /**
     * post请求，返回List类型数据
     * @param url
     * @param jsonParam
     * @param noNeedResponse
     * @return
     */
    public static List<Map<String, Object>> httpPostWithList(String url, JSONObject jsonParam){
        JSONArray jsonArray = httpPostWithJSONArray(url, jsonParam);
        if(jsonArray!=null){
            List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
            HashMap<String, Object> map = null;
            for(int i=0;i<jsonArray.size();i++){
                map = new HashMap<String, Object>();
                JSONObject jObject = (JSONObject)jsonArray.get(i);
                Iterator it = jObject.keys();
                // 遍历jsonObject数据，添加到Map对象
                while (it.hasNext()) {
                    String key = String.valueOf(it.next());
                    map.put(key, jObject.get(key));
                }
                data.add(map);
            }
            return data;
        }
        return null;
    }
    
    /**
     * post请求，返回List类型数据
     * @param url
     * @param paramJsonArray
     * @param noNeedResponse
     * @return
     */
    public static List<Map<String, Object>> httpPostWithList(String url, JSONArray paramJsonArray){
        JSONArray jsonArray = httpPostWithJSONArray(url, paramJsonArray);
        if(jsonArray!=null){
            List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
            HashMap<String, Object> map = null;
            for(int i=0;i<jsonArray.size();i++){
                map = new HashMap<String, Object>();
                JSONObject jObject = (JSONObject)jsonArray.get(i);
                Iterator it = jObject.keys();
                // 遍历jsonObject数据，添加到Map对象
                while (it.hasNext()) {
                    String key = String.valueOf(it.next());
                    map.put(key, jObject.get(key));
                }
                data.add(map);
            }
            return data;
        }
        return null;
    }
    
    /**
     * GET方式获取URL请求数据，返回String类型
     * @param appName
     * @param url
     * @return
     */
    public static String httpGet(String appName, String appDomain, String url){
        String requestUrl = getContext(appName,appDomain) + "/service" + url;
        return HttpRequestUtils.get(requestUrl);
    }
    
    /**
     * GET方式获取URL请求数据，返回Map类型
     * @param appName
     * @param url
     * @return
     */
    public static Map<String, Object> httpGetWithMap(String appName, String appDomain, String url){
        String requestUrl = getContext(appName,appDomain) + "/service" + url;
        String jsonStr = HttpRequestUtils.get(requestUrl);
        if(jsonStr!=null && !"".equals(jsonStr)){
            return JSONUtil.json2Bean(jsonStr, HashMap.class);
        }
        return null;
    }
    
    /**
     * GET方式获取URL请求数据，返回List类型
     * @param appName
     * @param url
     * @return
     */
    public static List<Map<String, Object>> httpGetWithList(String appName, String appDomain, String url){
        String requestUrl = getContext(appName,appDomain) + "/service" + url;
        String jsonStr = HttpRequestUtils.get(requestUrl);
        if(jsonStr!=null && !"".equals(jsonStr)){
            return JSONUtil.json2Bean(jsonStr, ArrayList.class);
        }
        return null;
    }
    
    /******
     *  获取应用地址
     * @param appName   [应用名称]
     * @param appDomain [配置文件中应用域的键值]
     * @return
     */
    public static String getContext(String appName,String appDomain) {
        // 获取集群配置中心地址
        String context = PropertiesUtil.getValue("conf.properties", appName);
        if (context == null || "".equals(context)) {
            context = appName.replace(".", "-");
        }
        // 若AppName以http开头，则直接返回，主要用于开发测试环境
        if (!context.startsWith("http")) {
            // 获取平台域名，用于生产环境
            String domainName = PropertiesUtil.getValue("conf.properties", appDomain);
            if (domainName != null && !"".equals(domainName)) {
                // 组装默认集群配置地址
                context = "http://" + domainName + "/" + context;
            } else {
            	logger.error("平台域名未配置！");
            }
        }
        if (context.endsWith("/")) {
            return context.substring(0, context.length()-1);
        }
        return context;
    }
    
}
