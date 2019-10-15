package com.chinaunicom.monitor.task;

import com.alibaba.fastjson.JSON;
import com.chinaunicom.log.LoggerHelp;
import com.chinaunicom.monitor.entity.InvigilatorDO;
import com.chinaunicom.monitor.entity.WarningDO;
import com.chinaunicom.monitor.service.JvmService;
import com.chinaunicom.monitor.service.MainframeService;
import com.chinaunicom.monitor.service.MonitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ljx
 */
@Service
public class AutoTask {

    @Autowired
    private JvmService jvmService;

    @Autowired
    private MainframeService mainframeService;

    @Autowired
    private MonitorService monitorService;

    public void checkLine() {
        WarningDO warningInfo = monitorService.getWarningInfo();
        String warningInfoStr = JSON.toJSONString(warningInfo);
        //我们的任务执行体
        System.out.println("warningInfo"+warningInfoStr);
        LoggerHelp.info(warningInfo);
    }

    public void checkClient() {
        //我们的任务执行体
        System.out.println("执行任务2");
    }
}
