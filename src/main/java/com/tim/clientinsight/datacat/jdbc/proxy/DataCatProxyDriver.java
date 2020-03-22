package com.tim.clientinsight.datacat.jdbc.proxy;

import com.tim.clientinsight.datacat.DataCatPlugin;
import com.tim.clientinsight.datacat.property.DataCatProperties;

import java.lang.reflect.Proxy;
import java.sql.*;
import java.util.Properties;
import java.util.logging.Logger;

public class DataCatProxyDriver implements Driver {

    private Driver proxyDriver;

    public DataCatProxyDriver() {
        try {
            this.proxyDriver = new DriverProxyAdapter().getDriver();
            DriverManager.registerDriver(this);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public Connection connect(String url, Properties info) throws SQLException {
        return (Connection) Proxy.newProxyInstance(getClass().getClassLoader(),
                new Class[]{Connection.class}, new ConnectionProxy(this.proxyDriver.connect(url, info)));
    }

    @Override
    public boolean acceptsURL(String url) throws SQLException {
        return this.proxyDriver.acceptsURL(url);
    }

    @Override
    public DriverPropertyInfo[] getPropertyInfo(String url, Properties info) throws SQLException {
        return this.proxyDriver.getPropertyInfo(url, info);
    }

    @Override
    public int getMajorVersion() {
        return this.proxyDriver.getMajorVersion();
    }

    @Override
    public int getMinorVersion() {
        return this.proxyDriver.getMinorVersion();
    }

    @Override
    public boolean jdbcCompliant() {
        return this.proxyDriver.jdbcCompliant();
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return this.proxyDriver.getParentLogger();
    }

    private static class DriverProxyAdapter {

        @SuppressWarnings("unchecked")
        private Driver getDriver() throws ClassNotFoundException, IllegalAccessException, InstantiationException {
            DataCatProperties dataCatProperties = DataCatPlugin.getPlugin().getDataCatProperties();
            Class<Driver> driverClass = (Class<Driver>) Class.forName(dataCatProperties.getJdbcDriver());
            return driverClass.newInstance();
        }
    }
}
