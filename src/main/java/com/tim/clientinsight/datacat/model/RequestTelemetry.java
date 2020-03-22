package com.tim.clientinsight.datacat.model;

import com.alibaba.fastjson.annotation.JSONField;

public class RequestTelemetry extends BaseTelemetry {

    @JSONField(name = "Key")
    private String key;
    @JSONField(name = "ContentLength")
    private Integer contentLength;
    @JSONField(name = "RequestContentLength")
    private Integer requestContentLength;
    @JSONField(name = "Route")
    private String route;
    @JSONField(name = "RouteNormalized")
    private String routeNormalized;
    @JSONField(name = "HttpMethod")
    private String httpMethod;
    @JSONField(name = "StatusCode")
    private Integer statusCode;
    @JSONField(name = "Requester")
    private String requester;
    @JSONField(name = "Context")
    private Context context;
    @JSONField(name = "TelemetryBag")
    private String telemetryBag;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Integer getContentLength() {
        return contentLength;
    }

    public void setContentLength(Integer contentLength) {
        this.contentLength = contentLength;
    }

    public Integer getRequestContentLength() {
        return requestContentLength;
    }

    public void setRequestContentLength(Integer requestContentLength) {
        this.requestContentLength = requestContentLength;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public String getRouteNormalized() {
        return routeNormalized;
    }

    public void setRouteNormalized(String routeNormalized) {
        this.routeNormalized = routeNormalized;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public String getRequester() {
        return requester;
    }

    public void setRequester(String requester) {
        this.requester = requester;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public String getTelemetryBag() {
        return telemetryBag;
    }

    public void setTelemetryBag(String telemetryBag) {
        this.telemetryBag = telemetryBag;
    }
}
