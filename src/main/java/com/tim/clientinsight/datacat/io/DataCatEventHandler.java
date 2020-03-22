package com.tim.clientinsight.datacat.io;

import com.lmax.disruptor.EventHandler;

public interface DataCatEventHandler extends EventHandler<DataCatEvent> {

    public abstract void shutdown();
}
