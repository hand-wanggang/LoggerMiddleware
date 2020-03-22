package com.tim.clientinsight.datacat.model;

import com.alibaba.fastjson.annotation.JSONField;

public class PlatformInfo {

    @JSONField(name = "OSVersion")
    private String osVersion;
    @JSONField(name = "TenantId")
    private String tenantId;
    @JSONField(name = "SdkPlatform")
    private String sdkPlatform;
    @JSONField(name = "SdkVersion")
    private String sdkVersion;
    @JSONField(name = "AppVersion")
    private String appVersion;
    @JSONField(name = "MachineName")
    private String machineName;
    @JSONField(name = "Host")
    private String host;

    public String getOsVersion() {
        return osVersion;
    }

    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getSdkPlatform() {
        return sdkPlatform;
    }

    public void setSdkPlatform(String sdkPlatform) {
        this.sdkPlatform = sdkPlatform;
    }

    public String getSdkVersion() {
        return sdkVersion;
    }

    public void setSdkVersion(String sdkVersion) {
        this.sdkVersion = sdkVersion;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getMachineName() {
        return machineName;
    }

    public void setMachineName(String machineName) {
        this.machineName = machineName;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void copyTo(PlatformInfo platformInfo) {
        platformInfo.setAppVersion(this.getAppVersion());
        platformInfo.setTenantId(this.getTenantId());
        platformInfo.setSdkVersion(this.getSdkVersion());
        platformInfo.setSdkPlatform(this.getSdkPlatform());
        platformInfo.setMachineName(this.getMachineName());
        platformInfo.setHost(this.getHost());
        platformInfo.setOsVersion(this.osVersion);
    }
}
