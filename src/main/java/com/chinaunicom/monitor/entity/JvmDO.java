package com.chinaunicom.monitor.entity;

import lombok.Data;

import java.util.List;

/**
 * @author ljx
 */
@Data
public class JvmDO {
    /**
     * 运行时情况
     */
    private RuntimeInfo runtimeInfo;
    /**
     * JVM 线程情况
     */
    private ThreadInfo threadInfo;
    /**
     * JVM 内存信息
     */
    private List<JvmMemoryInfo> jvmMemoryInfos;
}
