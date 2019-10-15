package com.chinaunicom.monitor.entity;

import lombok.Data;

/**
 * @author ljx
 */
@Data
public class CpuInfo {
    /**
     * cpu物理内核
     */
    private Integer physicalCpuCore;
    /**
     * cpu逻辑内核
     */
    private Integer logicalCpuCore;
    /**
     * 系统负载率
     */
    private String systemCpuLoad;
    /**
     * cpu使用率
     */
    private String cpuUsage;

}
