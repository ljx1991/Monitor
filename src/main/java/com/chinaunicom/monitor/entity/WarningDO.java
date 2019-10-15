package com.chinaunicom.monitor.entity;

import lombok.Data;

import java.util.List;

/**
 * @author ljx
 */
@Data
public class WarningDO {
    private List<WarningInfo> warningInfos;
}
