package org.loushang.framework.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import sdk.security.filter.SDKAuthzFilter;
import sdk.security.filter.SDKFilter;

@Configuration
public class SecurityConfig {
	
	//通用过滤器
	@Value("${security-sdk.filter.patterns:defaultNull}")
	private String filterUrlPatterns ;
	
	//权限过滤器
	@Value("${security-sdk.authzFilter.patterns:defaultNull}")
	private String authzFilterUrlPatterns;

	@Bean
    public FilterRegistrationBean sdkFilterRegistrationBean() {
		FilterRegistrationBean registration = new FilterRegistrationBean();
		registration.setFilter(new SDKFilter());
		registration.setName("SDKFilter");
		registration.setOrder(1);
		if("defaultNull".equals(filterUrlPatterns)){
			registration.addUrlPatterns("/*");
		}else{
			String[] urlPatterns = filterUrlPatterns.split(",");
			for (int i = 0; i < urlPatterns.length; i++) {
				registration.addUrlPatterns(urlPatterns[i]);
			}
		}
        return registration;
    }
	@Bean
	public FilterRegistrationBean sdkAuthzFilterRegistrationBean() {
		
		FilterRegistrationBean registration = new FilterRegistrationBean();
		registration.setFilter(new SDKAuthzFilter());
		registration.setName("SDKAuthzFilter");
		registration.setOrder(2);
		if("defaultNull".equals(authzFilterUrlPatterns)){
			return registration;
		}else{
			String[] urlPatterns = authzFilterUrlPatterns.split(",");
			for (int i = 0; i < urlPatterns.length; i++) {
				registration.addUrlPatterns(urlPatterns[i]);
			}
			return registration;
		}
	}

}
