package com.chinaunicom.monitor.entity;

import lombok.Data;

import java.util.List;

/**
 * @author ljx
 * 主机类监控
 */
@Data
public class MainframeDO {
    /**
     * 操作系统信息
     */
    private OperatingSystemInfo operatingSystemInfo;
    /**
     * 获取内存信息
     */
    private MemoryInfo memoryInfo;
    /**
     * cpu信息
     */
    private CpuInfo cpuInfo;
    /**
     * 硬盘信息
     */
    private List<DiskInfo> diskInfos;
    /**
     * 网络信息
     */
    private List<NetworkInfo> networkInfos;
}
