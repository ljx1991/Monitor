package com.chinaunicom.monitor.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * @author ljx
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MonitorDO {
    private String type;
    private MainframeDO mainframe;
    private JvmDO jvm;
    private String serverStatus;
    private ServerDO server;
}
