package org.loushang.framework.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

public class BusinessExceptionResolver extends SimpleMappingExceptionResolver {
	private Log logger = LogFactory.getLog(getClass());

	@Override
	protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {
		logger.error("异常信息", ex);
		String viewName = determineViewName(ex, request);
		if (viewName != null) {
			// 普通的页面提交方式，转向对应的异常处理页面
			if (!(request.getHeader("accept").indexOf("application/json") > -1
					|| (request.getHeader("X-Requested-With") != null
							&& request.getHeader("X-Requested-With").indexOf("XMLHttpRequest") > -1))) {
				Integer statusCode = determineStatusCode(request, viewName);
				if (statusCode != null) {
					applyStatusCodeIfPossible(request, response, statusCode);
				}
				return getModelAndView(viewName, ex, request);
			} else {// ajax提交方式，JSON格式返回异常信息
				try {
					response.setContentType("text/json;charset=UTF-8");
					if (ex.getMessage() != null) {
						response.getWriter().print(ex.getMessage());
					} else {
						response.getWriter().print("系统异常");
					}
					response.getWriter().close();
				} catch (Exception e) {
					logger.error(e);
				}
				return null;

			}
		} else {
			return null;
		}
	}
}
