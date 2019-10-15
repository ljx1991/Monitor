package com.chinaunicom.monitor.entity;

import lombok.Data;

/**
 * @author ljx
 */
@Data
public class JvmMemoryInfo {
    /**
     * JVM内存类型
     */
    private String memoryType;
    /**
     * 初始化堆内存
     */
    private String initMemory;
    /**
     * 已使用堆内存
     */
    private String usedMemory;
    /**
     * 可使用非堆内存
     */
    private String committedMemory;
    /**
     * 最大非堆内存
     */
    private String maxMemory;
}
