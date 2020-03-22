package com.tim.clientinsight.datacat;

import com.tim.clientinsight.datacat.interceptor.DataCatInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class DataCatWebMicConfigurer implements WebMvcConfigurer {

    private DataCatInterceptor dataCatInterceptor;

    public DataCatWebMicConfigurer(DataCatInterceptor dataCatInterceptor) {
        this.dataCatInterceptor = dataCatInterceptor;
    }

    public void addInterceptors(InterceptorRegistry interceptorRegistry){
        interceptorRegistry.addInterceptor(this.dataCatInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/static/**")
                .excludePathPatterns("/swagger-ui.html","/swagger-resources","/swagger-resources/**")
                .excludePathPatterns("/webjars/springfox-swagger-ui/**");
    }
}
