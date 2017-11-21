package org.loushang.ldf.autoconfigure;

import java.util.Properties;

import org.loushang.framework.exception.DefaultExceptionResolver;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass(DefaultExceptionResolver.class)
public class ExceptionAutoConfiguration {
	@Bean
	@ConditionalOnMissingBean(DefaultExceptionResolver.class)
	public DefaultExceptionResolver businessExceptionResolver() {
		DefaultExceptionResolver defaultExceptionResolver = new DefaultExceptionResolver();
		Properties properties = new Properties();
		properties.setProperty("org.loushang.framework.exception.BusinessException", "default/errorpage");
		properties.setProperty("java.lang.Exception", "default/500");
		properties.setProperty("java.lang.Throwable", "default/500");
		defaultExceptionResolver.setExceptionMappings(properties);
		return defaultExceptionResolver;
	}
}
