package com.tim.clientinsight.datacat;

import com.tim.clientinsight.datacat.model.PlatformInfo;

import java.net.InetAddress;
import java.util.List;
import java.util.Properties;

public class DataCatCollector {

    private PlatformInfo platformInfo = new PlatformInfo();
    private ThreadLocal<Long> timestamp = new ThreadLocal<>();
    private ThreadLocal<String> requester = new ThreadLocal<>();
    private ThreadLocal<String> requestId = new ThreadLocal<>();
    private ThreadLocal<List<String>> sequence = new ThreadLocal<>();

    private DataCatCollector() {
    }

    public PlatformInfo getPlatformInfo() {
        return platformInfo;
    }

    public ThreadLocal<String> getRequester() {
        return requester;
    }

    public ThreadLocal<String> getRequestId() {
        return requestId;
    }

    public ThreadLocal<List<String>> getSequence() {
        return sequence;
    }

    public static DataCatCollector getInstance() {
        return Singleton.dataCatCollector;
    }

    public ThreadLocal<Long> getTimestamp() {
        return timestamp;
    }

    public void clean() {
        requester.remove();
        requestId.remove();
        sequence.remove();
        timestamp.remove();
    }

    private static class Singleton {

        private static DataCatCollector dataCatCollector = new DataCatCollector();

        static {
            Properties props = System.getProperties();
            dataCatCollector.getPlatformInfo().setOsVersion(props.getProperty("os.version"));
            dataCatCollector.getPlatformInfo().setSdkPlatform(props.getProperty("java.vendor"));
            dataCatCollector.getPlatformInfo().setSdkVersion(props.getProperty("java.version"));
            dataCatCollector.getPlatformInfo().setAppVersion(props.getProperty("application.version"));
            dataCatCollector.getPlatformInfo().setMachineName(getHostName());
            dataCatCollector.getPlatformInfo().setHost(getHost());
        }

        private static String getHost() {
            try {
                InetAddress address = InetAddress.getLocalHost();
                return address.getHostAddress();
            } catch (Exception ex) {
                return null;
            }
        }

        private static String getHostName() {
            try {
                InetAddress address = InetAddress.getLocalHost();
                return address.getHostName();
            } catch (Exception ex) {
                return null;
            }
        }
    }
}
