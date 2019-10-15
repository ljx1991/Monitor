package com.chinaunicom.monitor.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

/**
 * @author ljx
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WarningInfo {
    /**
     * 服务名
     */
    private String serverName;
    /**
     * 应用名
     */
    private String serviceName;
    /**
     * 服务主机ip
     */
    private String serverIp;
    /**
     * 告警数量
     */
    private Integer warningNum;
    /**
     * 预警数量
     */
    private Integer earlyWarningNum;
    /**
     * 正常数量
     */
    private Integer normalNum;
    /**
     * 告警项
     */
    private List<WarnItem> warningItems;
    /**
     * 预警项
     */
    private List<WarnItem> earlyWarningItems;
    /**
     * 正常项
     */
    private List<WarnItem> normalItems;


}
