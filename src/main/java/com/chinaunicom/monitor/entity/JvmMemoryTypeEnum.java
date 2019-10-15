package com.chinaunicom.monitor.entity;

/**
 * @author ljx
 * jvm内存类型
 */
public enum JvmMemoryTypeEnum {
    /**
     * jvm内存为堆类型
     */
    HEAP("heap"),
    /**
     * jvm内存为非堆类型
     */
    NO_HEAP("noheap");

    private String memoryType;


    JvmMemoryTypeEnum(String memoryType) {
        this.memoryType = memoryType;
    }

    public String getType() {
        return this.memoryType;
    }

}
