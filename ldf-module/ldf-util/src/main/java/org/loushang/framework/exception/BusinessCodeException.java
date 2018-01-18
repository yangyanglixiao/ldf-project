package org.loushang.framework.exception;

import java.util.Locale;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.loushang.framework.util.PreferencesLoadUtil;
import org.loushang.framework.util.SpringContextHolder;
import org.springframework.context.MessageSource;

public class BusinessCodeException extends RuntimeException {

	private static Log log = LogFactory.getLog(BusinessCodeException.class);

	private static final long serialVersionUID = 1L;

	private MessageSource messageSource = SpringContextHolder.getBean("messageSource");

	private static Locale locale = new Locale("zh", "CN");

	static {
		String langType = null;
		Map<String, String> map = new PreferencesLoadUtil().getPreferences();
		if (map != null && map.size() > 0) {
			langType = map.get("langType");
		}
		if (langType != null && !"".equals(langType)) {
			try {
				String[] lang = langType.split("_");
				locale = new Locale(lang[0], lang[1]);
			} catch (Exception e) {
				log.error("解析本地语言配置失败，异常：", e);
			}

		}
	}

	private String errorCode = "-1";
	private String errorMsg = "系统异常";

	public BusinessCodeException() {
		super();
	}

	public BusinessCodeException(String code) {
		super();
		if (code != null && !"".equals(code)) {
			this.errorCode = code;
			// 根据Code获取异常信息
			this.errorMsg = messageSource.getMessage(code, null, locale);
		}
	}

	public BusinessCodeException(String code, String message) {
		super(message);
		if (code != null && !"".equals(code)) {
			this.errorCode = code;
			this.errorMsg = message;
		}
	}

	public BusinessCodeException(String code, String message, String[] param) {
		if (code != null && !"".equals(code)) {
			this.errorCode = code;
			if (param != null && param.length > 0) {
				for (int i = 0; i < param.length; i++) {
					if (param[i] != null) {
						message = message.replace("{" + i + "}", param[i]);
					} else {
						message = message.replace("{" + i + "}", "null");
					}
				}
			}
			this.errorMsg = message;
		}
		new BusinessException(message);
	}

	public BusinessCodeException(Throwable cause) {
		super(cause);
	}

	public BusinessCodeException(String code, Throwable cause) {
		super(cause);
		if (code != null && !"".equals(code)) {
			this.errorCode = code;
			// 根据Code获取异常信息
			this.errorMsg = messageSource.getMessage(code, null, locale);
		}
	}

	public BusinessCodeException(String code, String message, String[] param, Throwable cause) {
		if (code != null && !"".equals(code)) {
			this.errorCode = code;
			if (param != null && param.length > 0) {
				for (int i = 0; i < param.length; i++) {
					if (param[i] != null) {
						message = message.replace("{" + i + "}", param[i]);
					} else {
						message = message.replace("{" + i + "}", "null");
					}
				}
			}
			this.errorMsg = message;
		}
		new BusinessException(message, cause);
	}

	public BusinessCodeException(String code, String message, Throwable cause) {
		super(message, cause);
		if (code != null && !"".equals(code)) {
			this.errorCode = code;
			this.errorMsg = message;
		}
	}

	public String getErrorCode() {
		return errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public String getErrorInfo() {
		return "[" + errorCode + "] " + errorMsg;
	}

}
