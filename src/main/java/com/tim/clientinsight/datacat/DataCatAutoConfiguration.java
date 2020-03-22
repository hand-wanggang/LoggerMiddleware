package com.tim.clientinsight.datacat;

import com.tim.clientinsight.datacat.interceptor.DataCatInterceptor;
import com.tim.clientinsight.datacat.interceptor.TrackHttpInterceptor;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Configurable
public class DataCatAutoConfiguration {

    @Bean
    public DataCatInterceptor dataCatInterceptor() {
        return new DataCatInterceptor();
    }

    @Bean(destroyMethod = "shutdown")
    public DataCatPlugin dataCatPlugin() {
        return DataCatPlugin.getPlugin();
    }

    @Bean
    public DataCatWebMicConfigurer dataCatWebMicConfigurer(DataCatInterceptor dataCatInterceptor) {
        return new DataCatWebMicConfigurer(dataCatInterceptor);
    }

    @ConditionalOnBean(RestTemplate.class)
    @Bean
    public TrackHttpInterceptor trackHttpInterceptor(RestTemplate restTemplate) {
        TrackHttpInterceptor trackHttpInterceptor = new TrackHttpInterceptor();
        List<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
        interceptors.add(trackHttpInterceptor);
        if (null != restTemplate.getInterceptors()) {
            interceptors.addAll(restTemplate.getInterceptors());
        }
        restTemplate.setInterceptors(interceptors);
        return trackHttpInterceptor;
    }

}
