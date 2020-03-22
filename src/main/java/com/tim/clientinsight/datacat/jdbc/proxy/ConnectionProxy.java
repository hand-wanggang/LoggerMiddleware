package com.tim.clientinsight.datacat.jdbc.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.*;

public class ConnectionProxy implements InvocationHandler {

    private Connection connection;

    public ConnectionProxy(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        switch (method.getName()) {
            case "createStatement":
                return createStatement(proxy, method, args);
            case "prepareStatement":
                return prepareStatement(proxy, method, args);
            case "prepareCall":
                return prepareCall(proxy, method, args);
            default:
                return method.invoke(this.connection, args);
        }
    }

    private PreparedStatement prepareStatement(Object proxy, Method method, Object[] args) throws SQLException {

        PreparedStatement preparedStatement;
        try {
            preparedStatement = (PreparedStatement) method.invoke(this.connection, args);
        } catch (Exception e) {
            throw new SQLException(e);
        }
        String sql = null;
        if (null != args && args.length > 0) {
            sql = (String) args[0];
        }
        return (PreparedStatement) Proxy.newProxyInstance(getClass().getClassLoader(),
                new Class[]{PreparedStatement.class}, new PreparedStatementProxy(preparedStatement, sql));
    }

    private Statement createStatement(Object proxy, Method method, Object[] args) throws SQLException {
        Statement statement;
        try {
            statement = (Statement) method.invoke(this.connection, args);
        } catch (Exception e) {
            throw new SQLException(e);
        }
        return (Statement) Proxy.newProxyInstance(getClass().getClassLoader(),
                new Class[]{Statement.class}, new StatementProxy(statement));
    }

    private CallableStatement prepareCall(Object proxy, Method method, Object[] args) throws SQLException {
        CallableStatement callableStatement;
        try {
            callableStatement = (CallableStatement) method.invoke(this.connection, args);
        } catch (Exception ex) {
            throw new SQLException(ex);
        }
        String sql = null;
        if (null != args && args.length > 0) {
            sql = (String) args[0];
        }
        return (CallableStatement) Proxy.newProxyInstance(getClass().getClassLoader(),
                new Class[]{CallableStatement.class}, new CallableStatementProxy(sql, callableStatement));
    }
}
