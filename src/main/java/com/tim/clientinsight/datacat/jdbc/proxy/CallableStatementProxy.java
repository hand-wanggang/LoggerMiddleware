package com.tim.clientinsight.datacat.jdbc.proxy;

import com.alibaba.fastjson.JSON;
import com.tim.clientinsight.datacat.model.SqlTelemetry;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.sql.CallableStatement;
import java.util.HashMap;
import java.util.Map;

public class CallableStatementProxy extends StatementProxyCommon<CallableStatement> implements InvocationHandler {

    private final Map<String, Object> sqlParams = new HashMap<>();

    private String sql;
    private CallableStatement callableStatement;

    public CallableStatementProxy(String sql, CallableStatement callableStatement) {
        this.sql = sql;
        this.callableStatement = callableStatement;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        final String methodName = method.getName();
        if (methodName.startsWith("set") && null != args && args.length == 2) {
            sqlParams.put(String.valueOf(args[0]), args[1]);
        }
        return this.doInvoke(callableStatement, proxy, method, args);
    }

    @Override
    protected SqlTelemetry parseSqlTelemetry(Object proxy, Method method, Object[] args, long took, Exception ex) {
        SqlTelemetry sqlTelemetry = super.parseSqlTelemetry(proxy, method, args, took, ex);
        sqlTelemetry.setParameters(JSON.toJSONString(sqlParams));
        if (null != this.sql) {
            sqlTelemetry.setSql(this.sql);
        }
        return sqlTelemetry;
    }
}
