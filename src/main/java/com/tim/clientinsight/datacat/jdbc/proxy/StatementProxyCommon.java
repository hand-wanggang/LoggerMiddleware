package com.tim.clientinsight.datacat.jdbc.proxy;

import com.tim.clientinsight.datacat.DataCatCollector;
import com.tim.clientinsight.datacat.DataCatPlugin;
import com.tim.clientinsight.datacat.model.PlatformInfo;
import com.tim.clientinsight.datacat.model.SqlTelemetry;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class StatementProxyCommon<T> {

    private static final Logger LOG = LoggerFactory.getLogger(StatementProxyCommon.class);

    protected Object doInvoke(T statement, Object proxy, Method method, Object[] args) throws Throwable {
        long start = System.currentTimeMillis();
        long took = 0L;
        boolean executeFlag = method.getName().contains("execute");
        try {
            Object result = method.invoke(statement, args);
            took = System.currentTimeMillis() - start;
            if (executeFlag) {
                dataCatCollect(proxy, method, args, took, null);
            }
            return result;
        } catch (Exception ex) {
            if (executeFlag) {
                dataCatCollect(proxy, method, args, took, ex);
            }
            throw ex;
        }
    }

    protected SqlTelemetry parseSqlTelemetry(Object proxy, Method method, Object[] args, long took, Exception ex) {
        SqlTelemetry sqlTelemetry = new SqlTelemetry();
        sqlTelemetry.setTelemetryType("SqlTelemetry");
        sqlTelemetry.setTook(took);
        sqlTelemetry.setSuccess(ex == null);
        sqlTelemetry.setCompleted(ex == null);
        if (null != args && args.length > 0) {
            sqlTelemetry.setSql(args[0].toString());
        }
        String referenceId = DataCatCollector.getInstance().getRequestId().get();
        String id = UUID.randomUUID().toString();
        if (null == referenceId) {
            referenceId = UUID.randomUUID().toString();
            sqlTelemetry.setSequence(id);
        } else {
            List<String> sequences = DataCatCollector.getInstance().getSequence().get();
            sequences.add(id);
            sqlTelemetry.setSequence(StringUtils.join(sequences, ","));
        }
        sqlTelemetry.setTimestamp(new Date());
        sqlTelemetry.setReferenceId(referenceId);
        sqlTelemetry.setId(id);
        PlatformInfo platformInfo = DataCatCollector.getInstance().getPlatformInfo();
        platformInfo.copyTo(sqlTelemetry);
        return sqlTelemetry;
    }

    protected void dataCatCollect(Object proxy, Method method, Object[] args, long took, Exception ex) {
        try {
            SqlTelemetry sqlTelemetry = parseSqlTelemetry(proxy, method, args, took, ex);
            DataCatPlugin.getPlugin().collect(sqlTelemetry);
        } catch (Exception e) {
            LOG.error("error in data cat:", e);
        }
    }
}
