package org.loushang.ldf.util;

import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties(prefix = "cors")
public class CorsFilterProperties {
    private String allowOrigin = "*";
    private String allowMethods = "";
    private String allowCredentials = "";
    private String allowHeaders = "";
    private String exposeHeaders = "";

    public String getAllowOrigin() {
        return allowOrigin;
    }

    public void setAllowOrigin(String allowOrigin) {
        this.allowOrigin = allowOrigin;
    }

    public String getAllowMethods() {
        return allowMethods;
    }

    public void setAllowMethods(String allowMethods) {
        this.allowMethods = allowMethods;
    }

    public String getAllowCredentials() {
        return allowCredentials;
    }

    public void setAllowCredentials(String allowCredentials) {
        this.allowCredentials = allowCredentials;
    }

    public String getAllowHeaders() {
        return allowHeaders;
    }

    public void setAllowHeaders(String allowHeaders) {
        this.allowHeaders = allowHeaders;
    }

    public String getExposeHeaders() {
        return exposeHeaders;
    }

    public void setExposeHeaders(String exposeHeaders) {
        this.exposeHeaders = exposeHeaders;
    }
}
