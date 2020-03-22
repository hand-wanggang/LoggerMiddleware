package com.tim.clientinsight.datacat.model;

import com.alibaba.fastjson.annotation.JSONField;

public class SqlTelemetry extends BaseTelemetry {

    @JSONField(name = "Sql")
    private String sql;
    @JSONField(name = "Parameters")
    private String parameters;
    @JSONField(name = "Success")
    private Boolean success;
    @JSONField(name = "Completed")
    private Boolean completed;

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public String getParameters() {
        return parameters;
    }

    public void setParameters(String parameters) {
        this.parameters = parameters;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }
}
