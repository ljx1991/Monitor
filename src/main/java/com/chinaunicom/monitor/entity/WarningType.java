package com.chinaunicom.monitor.entity;

/**
 * @author ljx
 */

public enum WarningType {

    /**
     * 正常类型
     */
    NORMAL(1),

    /**
     * 告警类型
     */
    WARNING(-1),

    /**
     * 预警类型
     */
    EARLY_WARNING(0);

    private int type;

    WarningType(int type) {
        this.type = type;
    }

    public int getType(){
        return this.type;
    }


}
