package com.tim.clientinsight.datacat.model;

import com.alibaba.fastjson.annotation.JSONField;

public class Context {

    @JSONField(name = "request_header")
    private String requestHeaders;
    @JSONField(name = "response_headers")
    private String responseHeaders;
    @JSONField(name = "request_body")
    private String requestBody;
    @JSONField(name = "uri")
    private Uri uri;

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    public String getRequestHeaders() {
        return requestHeaders;
    }

    public void setRequestHeaders(String requestHeaders) {
        this.requestHeaders = requestHeaders;
    }

    public String getResponseHeaders() {
        return responseHeaders;
    }

    public void setResponseHeaders(String responseHeaders) {
        this.responseHeaders = responseHeaders;
    }

    public String getRequestBody() {
        return requestBody;
    }

    public void setRequestBody(String requestBody) {
        this.requestBody = requestBody;
    }

    public static class Uri{

        private Boolean isHttps;
        private String host;
        private String path;
        private String queryString;

        public Boolean getHttps() {
            return isHttps;
        }

        public void setHttps(Boolean https) {
            isHttps = https;
        }

        public String getHost() {
            return host;
        }

        public void setHost(String host) {
            this.host = host;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public String getQueryString() {
            return queryString;
        }

        public void setQueryString(String queryString) {
            this.queryString = queryString;
        }
    }
}
