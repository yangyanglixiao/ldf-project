package org.loushang.framework.i18n;

import java.util.Locale;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;

/**
 * 国际化控制器
 * 
 */
@Controller
@RequestMapping(value = "/i18n/resources")
public class I18nRestService {
    
    private static Log logger = LogFactory.getLog(I18nRestService.class);
	
    @Autowired
	private MessageSourceExt messageSourceExt;
    
    @Autowired
    private CookieLocaleResolver resolver;
	/**
	 * 基于Cookie的语言切换
	 * @return status 1：设置成功 0：设置失败
	 */
	@RequestMapping("/language/{language}")
	@ResponseBody
	public String language(HttpServletRequest request,HttpServletResponse response,@PathVariable String language){
	    String status = "1";
		if(language!=null && !"".equals(language)){
		    String[] array = language.split("_");
		    try {
	            //CookieLocaleResolver resolver = new CookieLocaleResolver();
	            resolver.setLocale(request, response, new Locale(array[0],array[1]));
	            Cookie cookie = new Cookie(resolver.getCookieName(), language);
	            response.addCookie(cookie);
            } catch (NoSuchBeanDefinitionException e) {
                logger.error(e);
                status = "0";
            } catch (Exception e) {
                logger.error(e);
                status = "0";
            }
		}
		return status;
	}
	
	/**
	 * 获取国际化资源
	 * @param request
	 * @param locale
	 * @return
	 */
	@RequestMapping
    @ResponseBody
    public Map<String,Object> resources(HttpServletRequest request,
                @RequestParam(value="locale", required=false) String locale){
        
        Locale clientLocale = null;
        //创建Locale对象
        if(locale!=null && !"".equals(locale)&&locale.indexOf("_")!=-1){
            String[] tmp = locale.split("_");
            clientLocale = new Locale(tmp[0],tmp[1]);
        }
        if(clientLocale==null){ //从Cookie中获取
            try {
            	//CookieLocaleResolver resolver = new CookieLocaleResolver();
                Cookie[] cookies = request.getCookies();
                for(Cookie cookie : cookies){
                    if(cookie.getName().equals(resolver.getCookieName())){
                        if(cookie.getValue()!=null && !"".equals(cookie.getValue())){
                            String[] tmp = cookie.getValue().split("_");
                            clientLocale = new Locale(tmp[0],tmp[1]);
                        }
                        break;
                    }
                }
            } catch (Exception e) {                
            }
        }
        if(clientLocale ==null){ //默认配置
            clientLocale = messageSourceExt.getServerLocal();
        }
        return messageSourceExt.getResources(clientLocale);
    }
	
	/**
     * 获取国际化信息
     * @param code 资源文件中编码
     * @return
     */
    @RequestMapping("{code:.+}")
    @ResponseBody
    public String getLocaleMessageRest(@PathVariable String code){
        return messageSourceExt.getLocaleMessage(code);
    }

}
