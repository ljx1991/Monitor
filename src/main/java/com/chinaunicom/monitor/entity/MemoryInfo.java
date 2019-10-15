package com.chinaunicom.monitor.entity;

import lombok.Data;

/**
 * @author ljx
 * 内存
 */
@Data
public class MemoryInfo {
    /**
     * 总的物理内存
     */
    private String memoryTotal;
    /**
     * 已使用的物理内存
     */
    private String memoryUsed;
    /**
     * 剩余的物理内存
     */
    private String memoryAvailable;
    /**
     * 最大可使用内存
     */
    private String swapTotal;
    /**
     * 剩余内存
     */
    private String swapUsed;
    /**
     *
     * 可使用内存
     */
    private String swapAvailable;
}
