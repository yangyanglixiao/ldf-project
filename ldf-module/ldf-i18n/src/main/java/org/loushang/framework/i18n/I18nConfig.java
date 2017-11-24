package org.loushang.framework.i18n;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;

@Configuration
public class I18nConfig {
	
	@Value("${spring.messages.language:zh_CN}")
	private String serverLocale;

	private static Locale cacheServerLocale = null;

	@Bean(name = "localeResolver")
	public CookieLocaleResolver cookieLocaleResolver() {
		if (serverLocale != null && "auto".equalsIgnoreCase(serverLocale)) {
			cacheServerLocale = LocaleContextHolder.getLocale();
		} else if (serverLocale != null && serverLocale.contains("_")) {
			String[] lang_country = serverLocale.split("_");
			cacheServerLocale = new Locale(lang_country[0], lang_country[1]);
		} else { // 默认
			cacheServerLocale = new Locale("zh", "CN");
		}

		CookieLocaleResolver resolver = new CookieLocaleResolver();
		resolver.setCookieName("Language");
		resolver.setCookieMaxAge(604800);
		resolver.setDefaultLocale(cacheServerLocale);
		return resolver;
	}
}
