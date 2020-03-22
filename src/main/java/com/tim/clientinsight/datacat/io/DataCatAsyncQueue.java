package com.tim.clientinsight.datacat.io;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.SleepingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.tim.clientinsight.datacat.model.BaseTelemetry;

import java.util.concurrent.Executors;

public class DataCatAsyncQueue {

    private int ringBufferSize;
    private Disruptor<DataCatEvent> disruptor;
    private DataCatEventHandler dataCatEventHandler;

    public DataCatAsyncQueue(int ringBufferSize, DataCatEventHandler dataCatEventHandler) {
        this.ringBufferSize = ringBufferSize;
        this.dataCatEventHandler = dataCatEventHandler;
    }

    public void start(){
        DataCatEventFactory dataCatEventFactory = new DataCatEventFactory();
        this.disruptor = new Disruptor<DataCatEvent>(dataCatEventFactory, ringBufferSize,
                Executors.defaultThreadFactory(), ProducerType.MULTI, new SleepingWaitStrategy());
        disruptor.handleEventsWith(this.dataCatEventHandler);
        disruptor.start();
    }

    public void shutdown(){
        this.disruptor.shutdown();
    }

    public void push(BaseTelemetry object){
        RingBuffer<DataCatEvent> ringBuffer = disruptor.getRingBuffer();
        long sequence = ringBuffer.next();
        try{
            DataCatEvent dataCatEvent = ringBuffer.get(sequence);
            dataCatEvent.setValue(object);
        }finally {
            ringBuffer.publish(sequence);
        }
    }
}
