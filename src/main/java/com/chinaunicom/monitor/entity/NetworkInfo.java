package com.chinaunicom.monitor.entity;

import lombok.Data;

/**
 * @author ljx
 * 磁盘使用率
 */
@Data
public class NetworkInfo {
    /**
     * net名称
     */
    private String netName;
    /**
     * ipV4地址
     */
    private String ipV4;
    /**
     * ipV6地址
     */
    private String ipV6;
}
