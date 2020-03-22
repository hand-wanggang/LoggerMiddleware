package com.tim.clientinsight.datacat.jdbc.proxy;


import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.sql.Statement;

public class StatementProxy extends StatementProxyCommon<Statement> implements InvocationHandler {

    private Statement statement;

    public StatementProxy(Statement statement) {
        this.statement = statement;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return this.doInvoke(statement, proxy, method, args);
    }
}
