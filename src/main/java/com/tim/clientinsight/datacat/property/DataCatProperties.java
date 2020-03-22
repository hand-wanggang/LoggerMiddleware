package com.tim.clientinsight.datacat.property;

public class DataCatProperties {

    private String tenantId;
    private Integer buffSize;
    private String jdbcDriver;
    private String exclude;
    private String requestTraceKey;
    private ElasticProperties elastic;

    public DataCatProperties() {
        this.buffSize = 1024 * 1024;
        this.requestTraceKey = "x-citiportal-requestId";
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public Integer getBuffSize() {
        return buffSize;
    }

    public void setBuffSize(Integer buffSize) {
        this.buffSize = buffSize;
    }

    public ElasticProperties getElastic() {
        return elastic;
    }

    public void setElastic(ElasticProperties elastic) {
        this.elastic = elastic;
    }

    public String getJdbcDriver() {
        return jdbcDriver;
    }

    public void setJdbcDriver(String jdbcDriver) {
        this.jdbcDriver = jdbcDriver;
    }

    public String getExclude() {
        return exclude;
    }

    public void setExclude(String exclude) {
        this.exclude = exclude;
    }

    public String getRequestTraceKey() {
        return requestTraceKey;
    }

    public void setRequestTraceKey(String requestTraceKey) {
        this.requestTraceKey = requestTraceKey;
    }
}
