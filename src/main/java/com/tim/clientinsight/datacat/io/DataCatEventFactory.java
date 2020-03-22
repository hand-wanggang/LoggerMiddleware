package com.tim.clientinsight.datacat.io;

import com.lmax.disruptor.EventFactory;

public class DataCatEventFactory implements EventFactory<DataCatEvent> {

    @Override
    public DataCatEvent newInstance() {
        return new DataCatEvent();
    }
}
