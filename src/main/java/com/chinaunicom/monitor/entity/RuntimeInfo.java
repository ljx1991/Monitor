package com.chinaunicom.monitor.entity;

import lombok.Data;

/**
 * @author ljx
 * 运行时情况
 */
@Data
public class RuntimeInfo {
    /**
     * JVM名称
     */
    private String jvmName;
    /**
     * JVM Java版本
     */
    private String javaVersion;
    /**
     * Java虚拟机的启动时间
     */
    private String startTime;
    /**
     * Java虚拟机的正常运行时间
     */
    private String upTime;

}
