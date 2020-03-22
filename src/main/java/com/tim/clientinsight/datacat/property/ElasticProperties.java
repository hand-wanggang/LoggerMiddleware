package com.tim.clientinsight.datacat.property;

public class ElasticProperties {

    private String host;
    private Integer executePort;
    private Integer queryPort;
    private String scheme;
    private String index;
    private String indexType;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getExecutePort() {
        return executePort;
    }

    public Integer getQueryPort() {
        return queryPort;
    }

    public void setQueryPort(Integer queryPort) {
        this.queryPort = queryPort;
    }

    public void setExecutePort(Integer executePort) {
        this.executePort = executePort;
    }

    public String getScheme() {
        return scheme;
    }

    public void setScheme(String scheme) {
        this.scheme = scheme;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getIndexType() {
        return indexType;
    }

    public void setIndexType(String indexType) {
        this.indexType = indexType;
    }
}
