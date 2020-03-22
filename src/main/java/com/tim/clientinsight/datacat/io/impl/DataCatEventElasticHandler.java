package com.tim.clientinsight.datacat.io.impl;

import com.alibaba.fastjson.JSON;
import com.tim.clientinsight.datacat.io.DataCatEvent;
import com.tim.clientinsight.datacat.io.DataCatEventHandler;
import com.tim.clientinsight.datacat.property.DataCatProperties;
import com.tim.clientinsight.datacat.property.ElasticProperties;
import org.apache.http.HttpHost;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataCatEventElasticHandler implements DataCatEventHandler {

    private static final Logger LOG = LoggerFactory.getLogger(DataCatEventElasticHandler.class);

    private ElasticProperties elasticProperties;
    private RestHighLevelClient restHighLevelClient;

    public DataCatEventElasticHandler(DataCatProperties dataCatProperties) {
        this.elasticProperties = dataCatProperties.getElastic();
        this.restHighLevelClient = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost(this.elasticProperties.getHost(), this.elasticProperties.getQueryPort(), this.elasticProperties.getScheme()),
                        new HttpHost(this.elasticProperties.getHost(), this.elasticProperties.getExecutePort(), this.elasticProperties.getScheme()))
        );
    }

    @Override
    public void onEvent(DataCatEvent dataCatEvent, long l, boolean b) throws Exception {

        LOG.debug("data:{}", JSON.toJSONString(dataCatEvent.getValue()));
        if (null != this.restHighLevelClient) {
            try {
                IndexRequest indexRequest = new IndexRequest(this.elasticProperties.getIndex(),
                        this.elasticProperties.getIndexType(), dataCatEvent.getValue().getId());
                indexRequest.source(JSON.toJSON(dataCatEvent.getValue()));
                this.restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
            } catch (Exception ex) {
                LOG.error("send data to elastic error:", ex);
            }
        }
    }

    @Override
    public void shutdown() {
        try {
            this.restHighLevelClient.close();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
