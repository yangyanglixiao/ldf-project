package org.loushang.ldf.autoconfigure;

import java.util.Properties;

import org.loushang.framework.exception.BusinessExceptionResolver;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass(BusinessExceptionResolver.class)
public class ExceptionAutoConfiguration {
	@Bean
	@ConditionalOnMissingBean(BusinessExceptionResolver.class)
	public BusinessExceptionResolver businessExceptionResolver() {
		BusinessExceptionResolver businessExceptionResolver = new BusinessExceptionResolver();
		Properties properties = new Properties();
		properties.setProperty("com.inspur.bigdata.analysis.exception.BusinessException", "public/errorpage");
		properties.setProperty("java.lang.Exception", "public/500");
		properties.setProperty("java.lang.Throwable", "public/500");
		businessExceptionResolver.setExceptionMappings(properties);
		return businessExceptionResolver;
	}
}
