package org.loushang.framework.i18n;

import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TreeMap;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
@Component
public class MessageSourceExt {
	
    private static Locale cacheServerLocale = null;
	
    @Value("${spring.messages.language:zh_CN}")
	private String serverLocale;

    @Resource
	private MessageSource messageSource;
    @Resource
    private ResourceBundleMessageSource resourceBundleMessageSource;
    @Autowired
    private CookieLocaleResolver resolver;
    /**
     * 获取国际化信息
     * @param code 资源文件中编码
     * @return
     */
    public String getLocaleMessage(String code){
    	Locale locale = getServerLocal();
        return messageSource.getMessage(code, null,locale);
    }
        
	/**
	 * 获取某个编码对应的信息，如果取不到值返回默认消息。
	 * 
	 * @param code
	 * @return
	 */
	public String getLocaleMessage(String code, String defaultMsg) {
		Locale locale = getServerLocal();
		return messageSource.getMessage(code, null,defaultMsg,locale);
	}
	
	/**
	 * 获取某个编码对应的信息，如果取不到值返回默认消息。支持参数化
	 * 
	 * @param code
	 * @param args
	 * @return
	 */
	public String getLocaleMessage(String code, String defaultMsg, Object[] args) {
		Locale locale = getServerLocal();
		return messageSource.getMessage(code, args, defaultMsg, locale);
	}

	/**
	 * 获取日期的国际信息 --Date 类型的参数
	 * 
	 * @param date
	 */
	public String getLocaleDate(Date date) {
		DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM, getServerLocal());
		return df.format(date);
	}

	/**
	 * 获取日期的国际化信息 --String 类型的参数
	 * 
	 * @param date
	 */
	public String getLocaleDate(String dateStr) {
		DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM, getServerLocal());
		Date date = null;
		SimpleDateFormat formater = new SimpleDateFormat();
		formater.applyPattern("yyy-MM-dd");
		try {
			date = formater.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return df.format(date);
	}
	
	public String getLangueType(HttpServletRequest request){
		try {
           // CookieLocaleResolver resolver = new CookieLocaleResolver();
            Cookie[] cookies = request.getCookies();
            for(Cookie cookie : cookies){
                if(cookie.getName().equals(resolver.getCookieName())){
                    if(cookie.getValue()!=null && !"".equals(cookie.getValue())){
                        return cookie.getValue();
                    }
                }
            }
        } catch (Exception e) {            
        }
        return serverLocale;
	}
	public Locale getServerLocal() {
		if (cacheServerLocale != null) {
			return cacheServerLocale;
		}
		if(serverLocale!=null && "auto".equalsIgnoreCase(serverLocale)){
			cacheServerLocale =  LocaleContextHolder.getLocale();
		}else if (serverLocale!=null && serverLocale.contains("_")) {
            String[] lang_country = serverLocale.split("_");
            cacheServerLocale = new Locale(lang_country[0], lang_country[1]);
        }else{ //默认
            cacheServerLocale = new Locale("zh", "CN");
        }
        return cacheServerLocale;
	}
	public Map<String, Object> getResources(Locale locale){
		// 返回资源和语言类型
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("locale", locale);
        TreeMap<String, String> treeMap = new TreeMap<String, String>();
        Set<String> baseNames = resourceBundleMessageSource.getBasenameSet();
        for (String baseName : baseNames) {
            treeMap.putAll(getKeyValues(baseName, locale));
        	
        }
        result.put("resources", treeMap);
        return result;
	}
	
	private Map<String, String> getKeyValues(String basename, Locale locale) {
        TreeMap<String, String> treeMap = new TreeMap<String, String>();
        ResourceBundle.clearCache();//解决读取资源文件中文乱码问题
        ResourceBundle bundle = ResourceBundle.getBundle(basename,locale);
        if (bundle != null) {
            for (String key : bundle.keySet()) {
                try {
					treeMap.put(key, new String(bundle.getString(key).getBytes("ISO-8859-1"), "UTF-8"));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
            }
        }
        ResourceBundle.clearCache();//解决读取资源文件中文乱码问题
        return treeMap;
    }
}
