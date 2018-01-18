package org.loushang.ldf.aspect;

import org.loushang.ldf.response.MyDataResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * Title: 全局异常处理切面 Description: 利用 @ControllerAdvice + @ExceptionHandler
 * 组合处理Controller层RuntimeException异常
 */
@ControllerAdvice   // 控制器增强
@ResponseBody
public class ExceptionAspect  implements ResponseBodyAdvice<Object> {

    /**
     * Log4j日志处理(@author: rico)
     */
    private static final Logger log = LoggerFactory.getLogger(ExceptionAspect.class);

    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return true;
    }

    /**
     * 处理controller层正常返回值
     * @param body
     * @param returnType
     * @param selectedContentType
     * @param selectedConverterType
     * @param request
     * @param response
     * @return
     */
    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType,
                                  MediaType selectedContentType, Class selectedConverterType,
                                  ServerHttpRequest request, ServerHttpResponse response) {

        ResponseException anno = returnType.getMethodAnnotation(ResponseException.class);
        if (anno==null) {
            MyDataResponse resp = new MyDataResponse();
            resp.success(body);
            return resp;
        }
        return body;
    }

    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public MyDataResponse handleHttpMessageNotReadableException(
            HttpMessageNotReadableException e) {
        log.error("could_not_read_json...", e);
        return new MyDataResponse().failure("could_not_read_json",e.toString());
    }

    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public MyDataResponse handleValidationException(MethodArgumentNotValidException e) {
        log.error("parameter_validation_exception...", e);
        return new MyDataResponse().failure("parameter_validation_exception",e.toString());
    }

    /**
     * 405 - Method Not Allowed。HttpRequestMethodNotSupportedException
     * 是ServletException的子类,需要Servlet API支持
     */
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public MyDataResponse handleHttpRequestMethodNotSupportedException(
            HttpRequestMethodNotSupportedException e) {
        log.error("request_method_not_supported...", e);
        return new MyDataResponse().failure("request_method_not_supported",e.toString());
    }

    /**
     * 415 - Unsupported Media Type。HttpMediaTypeNotSupportedException
     * 是ServletException的子类,需要Servlet API支持
     */
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    @ExceptionHandler({HttpMediaTypeNotSupportedException.class})
    public MyDataResponse handleHttpMediaTypeNotSupportedException(Exception e) {
        log.error("content_type_not_supported...", e);
        return new MyDataResponse().failure("content_type_not_supported",e.toString());
    }

    /**
     * 500 - Internal Server Error
     */
    //@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public MyDataResponse handleException(Exception e) {
        log.error("Internal Server Error...", e);
        return new MyDataResponse().failure("Internal Server Error",e.toString());
    }
}
