package com.chinaunicom.monitor.entity;

import lombok.Data;

/**
 * @author ljx
 * JVM 线程情况
 */
@Data
public class ThreadInfo {
    /**
     * 总线程数(守护+非守护)
     */
    private String threadCount;

    /**
     * 守护进程线程数
     */
    private String daemonThreadCount;

    /**
     * 峰值线程数
     */
    private String peakThreadCount;

    /**
     * Java虚拟机启动后创建并启动的线程总数
     */
    private String totalStartedThreadCount;
}
