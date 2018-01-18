package org.loushang.framework.exception;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

@ConfigurationProperties(prefix = "server")
public class DefaultExceptionResolver extends SimpleMappingExceptionResolver {

	private String servletPath = "";

	private String contextPath = "";

	public String getServletPath() {
		return servletPath;
	}

	public void setServletPath(String servletPath) {
		this.servletPath = servletPath;
	}

	public String getContextPath() {
		return contextPath;
	}

	public void setContextPath(String contextPath) {
		this.contextPath = contextPath;
	}

	private Log logger = LogFactory.getLog(getClass());

	@Override
	protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {
		logger.error("异常信息", ex);
		servletPath = getServletPath();
		contextPath = getContextPath();

		if (!"".equals(servletPath) && !servletPath.endsWith("/")) {
			servletPath += "/";
		}

		if (!"".equals(contextPath) && !contextPath.endsWith("/")) {
			contextPath += "/";
		}
		String viewName = determineViewName(ex, request);
		if (viewName != null) {
			// 普通的页面提交方式，转向对应的异常处理页面
			if (!(request.getHeader("accept").indexOf("application/json") > -1
					|| (request.getHeader("X-Requested-With") != null
							&& request.getHeader("X-Requested-With").indexOf("XMLHttpRequest") > -1))) {
				if ("default/500".equals(viewName)) {
					response.setContentType("text/html; charset=GBK");
					PrintWriter out = null;
					try {
						out = response.getWriter();
					} catch (IOException e) {
						e.printStackTrace();
					}
					out.println("<!DOCTYPE html>");
					out.println("<html lang=\"zh-CN\">");
					out.println("<head>");
					out.println("<meta charset=\"utf-8\">");
					out.println("<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">");
					out.println("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">");
					out.println("<!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->");
					out.println("<title>500</title>");
					out.println("<script src=" + contextPath + servletPath + "webjars/ldf/js/jquery.js></script>");
					out.println(" <!-- 需要引用的CSS -->");
					out.println("<link href=" + contextPath + servletPath
							+ "webjars/ldf/css/font-awesome.css rel=\"stylesheet\">");
					out.println(
							"<link href=" + contextPath + servletPath + "webjars/ldf/css/ui.css rel=\"stylesheet\">");
					out.println("<style type=\"text/css\">");

					out.println("body {");
					out.println("background-color: #fff;}");
					out.println(".content {");
					out.println("background-image: url(" + contextPath + servletPath + "skin/platform/img/500.png\");");
					out.println("background-repeat: no-repeat;");
					out.println("margin: 5% auto;");
					out.println("width: 590px;");
					out.println("height: 380px;}");
					out.println(".content .fa {");
					out.println("margin-right: 6px;");
					out.println("font-size: 18px;}");

					out.println("ul {");
					out.println("position: relative;");
					out.println("top: 280px;");
					out.println("padding-left: 20px;");
					out.println("padding-top: 10px;");
					out.println("font-family: Microsoft yahei;}");
					out.println("li {");
					out.println("line-height: 30px;");
					out.println("margin-left: 190px;");
					out.println("list-style:none;}");
					out.println("a {");
					out.println("color: #027bff;");
					out.println("text-decoration: none;}");
					out.println("a:hover {");
					out.println("color: blue;");
					out.println("text-decoration: underline;}");
					out.println("</style>");
					out.println(
							"<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->");
					out.println("<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->");
					out.println("<!--[if lt IE 9]>");
					out.println("<script src=\"" + contextPath + servletPath + "/js/html5shiv.js\"></script>");
					out.println("<script src=\"" + contextPath + servletPath + "/js/respond.js\"></script>");
					out.println("<![endif]-->");
					out.println("</head>");
					out.println("<body>");
					out.println("<div class=\"content\">");
					out.println("<ul>");
					out.println(
							"<li><a href=\"javascript:window.location.reload();\"><i class=\"fa fa-angle-double-left\"></i>\"刷新一下\"</a></li>\n"
									+ "			<li><a href=\"javascript:history.go(-1);\"><i class=\"fa fa-angle-double-left\"></i>\"返回上一页\"</a></li>");
					out.println("</ul>");
					out.println("<script type=\"text/javascript\">");
					out.println("var tmpUrl = \"" + contextPath + servletPath + "skin/platform/img/500.png\";");
					out.println("$(\".content\").css('background-image',\"url(\"+tmpUrl+\")\");");
					out.println("</script>");
					out.println("</body>");
					out.println("</html>");
					out.close();
					return null;
				} else if ("default/errorpage".equals(viewName)) {
					response.setContentType("text/html; charset=GBK");
					PrintWriter out = null;
					try {
						out = response.getWriter();
					} catch (IOException e) {
						e.printStackTrace();
					}

					out.println("<!DOCTYPE html>");
					out.println("<html lang=\"zh-CN\">");
					out.println("<head>");
					out.println("<meta charset=\"utf-8\">");
					out.println("<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">");
					out.println("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">");
					out.println("<!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->");
					out.println("<title>error</title>");
					out.println("<script src=" + contextPath + servletPath + "webjars/ldf/js/jquery.js></script>");
					out.println(" <!-- 需要引用的CSS -->");
					out.println("<link href=" + contextPath + servletPath
							+ "webjars/ldf/css/bootstrap.css rel=\"stylesheet\">");
					out.println("<link href=" + contextPath + servletPath
							+ "webjars/ldf/css/font-awesome.css rel=\"stylesheet\">");
					out.println(
							"<link href=" + contextPath + servletPath + "webjars/ldf/css/ui.css rel=\"stylesheet\">");
					out.println("<style type=\"text/css\">");
					out.println("body {");
					out.println("background-color: #fff;}");
					out.println(".imginfo {");
					out.println("background-image: url(\"" + contextPath + servletPath
							+ "skin/platform/img/errorpage.png\");");
					out.println("background-repeat: no-repeat;");
					out.println("margin: 5% auto;");
					out.println("margin-bottom: 0px;");
					out.println("width: 80%;");
					out.println("height: 200px;}");

					out.println(".detailinfo {");
					out.println("display: none;");
					out.println("margin: 0 auto;");
					out.println("width: 80%;");
					out.println("height: 300px;");
					out.println("overflow: auto;");
					out.println("border: 1px solid #ddd;");
					out.println("border-weight: bold;}");

					out.println(".ue-btn {");
					out.println("float: right;");
					out.println("margin-right: 25%;");
					out.println("background-color: #30c0d0;");
					out.println("color: #fff;");
					out.println("border-style: none;}");

					out.println(".btn.ue-btn:hover, .btn.ue-btn:focus {");
					out.println("color: #fff;");
					out.println("background-color: #42d2e2;}");

					out.println("</style>");

					out.println(
							"<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->");
					out.println("<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->");
					out.println("<!--[if lt IE 9]>");
					out.println("<script src=\"" + contextPath + servletPath + "/js/html5shiv.js\"></script>");
					out.println("<script src=\"" + contextPath + servletPath + "/js/respond.js\"></script>");
					out.println("<![endif]-->");

					out.println("</head>");
					out.println("<body style=\"margin: 0;padding: 0;\"> ");
					out.println("<div class=\"container\"> ");
					out.println("<div class=\"imginfo\"></div>");
					out.println("<a class=\"btn ue-btn pull-right\" id=\"infobtn\" >详细信息</a>");
					out.println("<div class=\"detailinfo\" id=\"info\">");
					out.println(ex.getMessage());
					out.println("</div>");
					out.println("</div>");

					out.println("<!-- 需要添加的JS -->");
					out.println("<script src=" + contextPath + servletPath + "webjars/ldf/js/jquery.js></script>");
					out.println("<script>");

					out.println("var tmpUrl = \"" + contextPath + servletPath + "skin/platform/img/errorpage.png\";");
					out.println("$(\".content\").css('background-image',\"url(\"+tmpUrl+\")\");");
					out.println("$(document).ready(function(){");
					out.println("$(\"#infobtn\").click(function(){");
					out.println("$(this).next().slideToggle(800);");
					out.println("})");
					out.println("})");
					out.println("</script>");
					out.println("</body>");
					out.println("</html>");
					out.close();
					return null;
				} else {
					Integer statusCode = determineStatusCode(request, viewName);
					if (statusCode != null) {
						applyStatusCodeIfPossible(request, response, statusCode);
					}
					return getModelAndView(viewName, ex, request);
				}
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
