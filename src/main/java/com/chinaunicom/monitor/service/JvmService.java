package com.chinaunicom.monitor.service;

import com.chinaunicom.monitor.entity.*;
import org.springframework.stereotype.Service;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryUsage;
import java.lang.management.RuntimeMXBean;
import java.lang.management.ThreadMXBean;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @author ljx
 */
@Service
public class JvmService {


    public JvmDO getJvm() {
        JvmDO jvm = new JvmDO();
        jvm.setRuntimeInfo(getRuntimeInfo());
        jvm.setJvmMemoryInfos(getJvmMemoryInfos());
        jvm.setThreadInfo(getThreadInfo());
        return jvm;
    }

    /**
     * 获取运行时情况
     *
     * @return
     */
    public RuntimeInfo getRuntimeInfo() {
        RuntimeInfo runtimeInfo = new RuntimeInfo();
        //运行时情况
        RuntimeMXBean runtime = ManagementFactory.getRuntimeMXBean();
        runtimeInfo.setJvmName(runtime.getVmName());
        runtimeInfo.setJavaVersion(System.getProperty("java.version"));
//        runtimeInfo.setStartTime(toDuration(runtime.getStartTime()));
        runtimeInfo.setUpTime(toDuration(runtime.getUptime()));
        return runtimeInfo;
    }

    /**
     * 获取线程信息
     *
     * @return
     */
    public ThreadInfo getThreadInfo() {
        ThreadInfo threadInfo = new ThreadInfo();
        //线程使用情况
        ThreadMXBean threads = ManagementFactory.getThreadMXBean();
        threadInfo.setThreadCount(String.valueOf(threads.getThreadCount()));
        threadInfo.setDaemonThreadCount(String.valueOf(threads.getDaemonThreadCount()));
        threadInfo.setPeakThreadCount(String.valueOf(threads.getPeakThreadCount()));
        threadInfo.setTotalStartedThreadCount(String.valueOf(threads.getTotalStartedThreadCount()));
        return threadInfo;
    }

    /**
     * 获取内存信息
     *
     * @return
     */
    public List<JvmMemoryInfo> getJvmMemoryInfos() {
        List<JvmMemoryInfo> jvmMemoryInfos = new ArrayList();
        //堆内存使用情况
        MemoryUsage heapMemoryUsage = ManagementFactory.getMemoryMXBean().getHeapMemoryUsage();
        JvmMemoryInfo jvmMemoryInfo = new JvmMemoryInfo();
        jvmMemoryInfo.setMemoryType(JvmMemoryTypeEnum.HEAP.getType());
        jvmMemoryInfo.setInitMemory(String.valueOf(heapMemoryUsage.getInit()));
        jvmMemoryInfo.setCommittedMemory(String.valueOf(heapMemoryUsage.getCommitted()));
        jvmMemoryInfo.setUsedMemory(String.valueOf(heapMemoryUsage.getUsed()));
        jvmMemoryInfo.setMaxMemory(String.valueOf(heapMemoryUsage.getMax()));
        jvmMemoryInfos.add(jvmMemoryInfo);
        //非堆内存使用情况
        MemoryUsage nonHeapMemoryUsage = ManagementFactory.getMemoryMXBean().getNonHeapMemoryUsage();
        jvmMemoryInfo = new JvmMemoryInfo();
        jvmMemoryInfo.setMemoryType(JvmMemoryTypeEnum.NO_HEAP.getType());
        jvmMemoryInfo.setInitMemory(String.valueOf(nonHeapMemoryUsage.getInit()));
        jvmMemoryInfo.setCommittedMemory(String.valueOf(nonHeapMemoryUsage.getCommitted()));
        jvmMemoryInfo.setUsedMemory(String.valueOf(nonHeapMemoryUsage.getUsed()));
        jvmMemoryInfo.setMaxMemory(String.valueOf(nonHeapMemoryUsage.getMax()));
        jvmMemoryInfos.add(jvmMemoryInfo);
        return jvmMemoryInfos;
    }


    private String toDuration(double uptime) {
        NumberFormat fmtI = new DecimalFormat("###,###", new DecimalFormatSymbols(Locale.ENGLISH));
        NumberFormat fmtD = new DecimalFormat("###,##0.000", new DecimalFormatSymbols(Locale.ENGLISH));
        uptime /= 1000;
        if (uptime < 60) {
            return fmtD.format(uptime) + " seconds";
        }
        uptime /= 60;
        if (uptime < 60) {
            long minutes = (long) uptime;
            String s = fmtI.format(minutes) + (minutes > 1 ? " minutes" : " minute");
            return s;
        }
        uptime /= 60;
        if (uptime < 24) {
            long hours = (long) uptime;
            long minutes = (long) ((uptime - hours) * 60);
            String s = fmtI.format(hours) + (hours > 1 ? " hours" : " hour");
            if (minutes != 0) {
                s += " " + fmtI.format(minutes) + (minutes > 1 ? " minutes" : " minute");
            }
            return s;
        }
        uptime /= 24;
        long days = (long) uptime;
        long hours = (long) ((uptime - days) * 24);
        String s = fmtI.format(days) + (days > 1 ? " days" : " day");
        if (hours != 0) {
            s += " " + fmtI.format(hours) + (hours > 1 ? " hours" : " hour");
        }
        return s;
    }

}

