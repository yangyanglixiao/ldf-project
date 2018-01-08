package org.loushang.ldf.webConfig;

import org.loushang.ldf.filter.CorsFilter;
import org.loushang.ldf.util.CorsFilterProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@EnableConfigurationProperties({CorsFilterProperties.class})
@Configuration
public class WebConfig {
    @Autowired
    private CorsFilterProperties corsFilterProperties;

    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        Map<String, String> map = new HashMap<>();
        map.put("allowOrigin", corsFilterProperties.getAllowOrigin());
        map.put("allowMethods", corsFilterProperties.getAllowMethods());
        map.put("allowCredentials", corsFilterProperties.getAllowCredentials());
        map.put("allowHeaders", corsFilterProperties.getAllowHeaders());
        map.put("exposeHeaders", corsFilterProperties.getExposeHeaders());

        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new CorsFilter());
        filterRegistrationBean.setInitParameters(map);
        filterRegistrationBean.setName("corsFilter");
        filterRegistrationBean.addUrlPatterns("/*");
        return filterRegistrationBean;
    }
}
