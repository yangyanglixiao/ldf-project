package org.loushang.framework.exception;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;

/**
 * 模块化输出日志信息到对应日志文件
 * 
 * @author 楼上项目组
 *
 *         <bean id="aspectBean" class=
 *         "org.loushang.framework.exception.ExceptionLogAspect" />
 * 
 *         spring-context.xml 进行如下配置 <aop:config>
 *         <aop:aspect id="logAspect" ref="aspectBean">
 *         <aop:pointcut id="businessService" expression="execution(*
 *         com.inspur..*.*(..))" />
 *         <aop:after-throwing pointcut-ref="businessService" method=
 *         "doThrowing" throwing="ex"/> </aop:aspect> </aop:config>
 */
public class ExceptionLogAspect {

	private Log logger;

	public void doThrowing(JoinPoint jp, Throwable ex) {
		logger = LogFactory.getLog(jp.getTarget().getClass());
		logger.error("", ex);
	}
}
