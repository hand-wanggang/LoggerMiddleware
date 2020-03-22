package com.tim.clientinsight.datacat.interceptor;

import com.tim.clientinsight.datacat.DataCatCollector;
import com.tim.clientinsight.datacat.DataCatPlugin;
import com.tim.clientinsight.datacat.model.PlatformInfo;
import com.tim.clientinsight.datacat.model.RequestTelemetry;
import com.tim.clientinsight.datacat.property.DataCatProperties;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class DataCatInterceptor implements HandlerInterceptor {

    private static final Logger LOG = LoggerFactory.getLogger(DataCatInterceptor.class);

    private static final String REQUESTER_KEY = "x-citiportal-LoginID";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        try {
            DataCatProperties dataCatProperties = DataCatPlugin.getPlugin().getDataCatProperties();
            String requestId = request.getHeader(dataCatProperties.getRequestTraceKey());
            requestId = null == requestId ? UUID.randomUUID().toString() : requestId;
            String requester = request.getHeader(REQUESTER_KEY);
            DataCatCollector.getInstance().getRequester().set(requester);
            DataCatCollector.getInstance().getRequestId().set(requestId);
            List<String> sequence = new ArrayList<>();
            sequence.add(UUID.randomUUID().toString());
            DataCatCollector.getInstance().getSequence().set(sequence);
            DataCatCollector.getInstance().getTimestamp().set(System.currentTimeMillis());
        } catch (Exception ex) {
            LOG.error("error in DataCatCollector preHandle:", ex);
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        try {
            final RequestTelemetry requestTelemetry = new RequestTelemetry();
            requestTelemetry.setId(DataCatCollector.getInstance().getSequence().get().get(0));
            requestTelemetry.setReferenceId(DataCatCollector.getInstance().getRequestId().get());
            requestTelemetry.setContentLength(request.getContentLength());
            requestTelemetry.setHttpMethod(request.getMethod());
            requestTelemetry.setRequestContentLength(request.getContentLength());
            requestTelemetry.setRequester(DataCatCollector.getInstance().getRequester().get());
            requestTelemetry.setRoute(request.getRequestURI());
            requestTelemetry.setRouteNormalized(request.getRequestURI());
            requestTelemetry.setSequence(StringUtils.join(DataCatCollector.getInstance().getSequence().get(), ","));
            requestTelemetry.setStatusCode(response.getStatus());
            requestTelemetry.setTelemetryType("RequestTelemetry");
            requestTelemetry.setTimestamp(new Date());
            requestTelemetry.setTook(System.currentTimeMillis() - DataCatCollector.getInstance().getTimestamp().get());
            PlatformInfo platformInfo = DataCatCollector.getInstance().getPlatformInfo();
            platformInfo.copyTo(requestTelemetry);
            DataCatPlugin.getPlugin().collect(requestTelemetry);
        } catch (Exception ex) {
            LOG.error("error in DataCatCollector postHandle :", ex);
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        DataCatCollector.getInstance().clean();
    }
}
