package com.tim.clientinsight.datacat.io.impl;

import com.alibaba.fastjson.JSON;
import com.tim.clientinsight.datacat.io.DataCatEvent;
import com.tim.clientinsight.datacat.io.DataCatEventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataCatEventConsoleHandler implements DataCatEventHandler {

    private static final Logger LOG = LoggerFactory.getLogger(DataCatEventConsoleHandler.class);

    @Override
    public void shutdown() {

    }

    @Override
    public void onEvent(DataCatEvent dataCatEvent, long l, boolean b) throws Exception {
        LOG.debug("data:{}", JSON.toJSONString(dataCatEvent.getValue()));
    }
}
