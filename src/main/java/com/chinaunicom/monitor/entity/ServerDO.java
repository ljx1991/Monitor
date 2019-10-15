package com.chinaunicom.monitor.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

/**
 * @author ljx
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ServerDO {
    private String serverName;
    private String serverUri;
    private List<MonitorDO> monitors;
}
