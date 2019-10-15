package com.chinaunicom.monitor.entity;

import lombok.Data;

/**
 * @author ljx
 * 硬盘信息
 */
@Data
public class DiskInfo {
    /**
     * 总磁盘空间
     */
    private String diskSize;
    /**
     * 已使用磁盘空间
     */
    private String diskUsedSize;
    /**
     * 磁盘使用率
     */
    private String diskUsageRate;
    /**
     * 磁盘挂载点
     */
    private String diskMount;
}
