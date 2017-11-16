package org.loushang.framework.util;

import java.io.IOException;
import java.io.StringWriter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * Java对象与JSON之间互相转换的工具类
 * 
 * @author zhenfeng
 * 
 */
public class JSONUtil {
	private static ObjectMapper mapper = new ObjectMapper();
	private static Log log = LogFactory.getLog(JSONUtil.class);

	public static String bean2Json(Object obj) {

		try {
			StringWriter sw = new StringWriter();
			JsonGenerator gen = new JsonFactory().createJsonGenerator(sw);
			mapper.writeValue(gen, obj);
			gen.close();
			return sw.toString();
		} catch (IOException e) {
			log.error(">>Transform object to json error.", e);
		}
		return null;
	}

	public static <T> T json2Bean(String jsonStr, Class<T> objClass) {
		try {
			T result = mapper.readValue(jsonStr, objClass);
			return result;
		} catch (IOException e) {
			log.error(">>Transform json to object error.", e);
		}

		return null;
	}
}
