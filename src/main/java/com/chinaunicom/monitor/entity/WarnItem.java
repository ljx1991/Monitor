package com.chinaunicom.monitor.entity;


import lombok.Data;

/**
 * @author ljx
 */
@Data
public class WarnItem {
    /**
     * 告警项名
     */
    private String itemName;
    /**
     * 告警项的值
     */
    private String itemValue;
    /**
     * 告警内容
     */
    private String warnLine;

}
