package com.tim.clientinsight.datacat.io;

import com.tim.clientinsight.datacat.model.BaseTelemetry;

public class DataCatEvent {

    private BaseTelemetry value;

    public BaseTelemetry getValue() {
        return value;
    }

    public void setValue(BaseTelemetry value) {
        this.value = value;
    }
}
