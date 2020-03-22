package com.tim.clientinsight.datacat.interceptor;

import com.tim.clientinsight.datacat.DataCatCollector;
import com.tim.clientinsight.datacat.DataCatPlugin;
import com.tim.clientinsight.datacat.property.DataCatProperties;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

public class TrackHttpInterceptor implements ClientHttpRequestInterceptor {

    @Override
    public ClientHttpResponse intercept(HttpRequest httpRequest, byte[] bytes, ClientHttpRequestExecution clientHttpRequestExecution) throws IOException {

        DataCatProperties dataCatProperties = DataCatPlugin.getPlugin().getDataCatProperties();
        String requestId = DataCatCollector.getInstance().getRequestId().get();
        if (null != requestId) {
            httpRequest.getHeaders().add(dataCatProperties.getRequestTraceKey(), requestId);
        }
        return clientHttpRequestExecution.execute(httpRequest, bytes);
    }
}
