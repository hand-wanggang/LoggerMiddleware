package com.tim.clientinsight.datacat.model;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

public class BaseTelemetry extends PlatformInfo {

    @JSONField(name = "ReferenceId")
    private String referenceId;
    @JSONField(name = "Id")
    private String id;
    @JSONField(name = "TelemetryType")
    private String telemetryType;
    @JSONField(name = "Region")
    private String region;
    @JSONField(name = "Took")
    private Long took;
    @JSONField(name = "Timestamp")
    private Date timestamp;
    @JSONField(name = "Sequence")
    private String sequence;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTelemetryType() {
        return telemetryType;
    }

    public void setTelemetryType(String telemetryType) {
        this.telemetryType = telemetryType;
    }

    public String getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(String referenceId) {
        this.referenceId = referenceId;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public Long getTook() {
        return took;
    }

    public void setTook(Long took) {
        this.took = took;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getSequence() {
        return sequence;
    }

    public void setSequence(String sequence) {
        this.sequence = sequence;
    }
}
