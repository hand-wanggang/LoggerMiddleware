package com.tim.clientinsight.datacat;

import com.tim.clientinsight.datacat.io.DataCatAsyncQueue;
import com.tim.clientinsight.datacat.io.DataCatEventHandler;
import com.tim.clientinsight.datacat.io.impl.DataCatEventConsoleHandler;
import com.tim.clientinsight.datacat.io.impl.DataCatEventElasticHandler;
import com.tim.clientinsight.datacat.model.BaseTelemetry;
import com.tim.clientinsight.datacat.property.DataCatProperties;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.InputStream;

public class DataCatPlugin {

    private String profile;
    private DataCatAsyncQueue dataCatAsyncQueue;
    private DataCatEventHandler dataCatEventHandler;
    private DataCatProperties dataCatProperties;

    private DataCatPlugin() {
        profile = "data-cat.yml";
        init();
    }

    private void init() {
        String env = System.getenv("SERVER_ENV");
        if (null != env) {
            this.profile = "data-cat-" + env + ".yml";
        }
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(this.profile);
        dataCatProperties = new Yaml(new Constructor(DataCatProperties.class)).load(inputStream);
        DataCatCollector.getInstance().getPlatformInfo().setTenantId(dataCatProperties.getTenantId());
        if (null != dataCatProperties.getElastic()) {
            this.dataCatEventHandler = new DataCatEventElasticHandler(dataCatProperties);
        } else {
            this.dataCatEventHandler = new DataCatEventConsoleHandler();
        }
        this.dataCatAsyncQueue = new DataCatAsyncQueue(dataCatProperties.getBuffSize(), this.dataCatEventHandler);
        this.dataCatAsyncQueue.start();
    }

    public void shutdown() {
        if (null != this.dataCatAsyncQueue) {
            this.dataCatAsyncQueue.shutdown();
        }
        if (null != this.dataCatEventHandler) {
            this.dataCatEventHandler.shutdown();
        }
    }

    public void collect(BaseTelemetry telemetry) {
        this.dataCatAsyncQueue.push(telemetry);
    }

    public static DataCatPlugin getPlugin() {
        return Singleton.plugin;
    }

    private static class Singleton {

        private static DataCatPlugin plugin = new DataCatPlugin();

    }

    public DataCatProperties getDataCatProperties() {
        return dataCatProperties;
    }
}
