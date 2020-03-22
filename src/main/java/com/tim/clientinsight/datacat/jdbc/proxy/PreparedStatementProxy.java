package com.tim.clientinsight.datacat.jdbc.proxy;

import com.alibaba.fastjson.JSON;
import com.tim.clientinsight.datacat.model.SqlTelemetry;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.sql.PreparedStatement;
import java.util.*;

public class PreparedStatementProxy extends StatementProxyCommon<PreparedStatement> implements InvocationHandler {

    private final Map<Integer, Object> sqlParams = new HashMap<>();

    private String sql;
    private PreparedStatement preparedStatement;

    public PreparedStatementProxy(PreparedStatement preparedStatement, String sql) {
        this.preparedStatement = preparedStatement;
        this.sql = sql;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        final String methodName = method.getName();
        if (methodName.startsWith("set") && null != args && args.length == 2) {
            sqlParams.put((Integer) args[0], args[1]);
        }
        return this.doInvoke(preparedStatement, proxy, method, args);
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
