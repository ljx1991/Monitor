package com.chinaunicom.monitor.controller;

import com.chinaunicom.monitor.entity.InvigilatorDO;
import com.chinaunicom.monitor.entity.JvmDO;
import com.chinaunicom.monitor.entity.MainframeDO;
import com.chinaunicom.monitor.entity.WarningDO;
import com.chinaunicom.monitor.service.JvmService;
import com.chinaunicom.monitor.service.MainframeService;
import com.chinaunicom.monitor.service.MonitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author ljx
 */

@Controller
public class MonitorController {
    @Autowired
    private JvmService jvmService;

    @Autowired
    private MainframeService mainframeService;

    @Autowired
    private MonitorService monitorService;


    @RequestMapping({"jvm"})
    public @ResponseBody
    JvmDO getJvm() {
        return jvmService.getJvm();
    }

    @RequestMapping({"mainframe"})
    public @ResponseBody
    MainframeDO getMainframe() {
        return mainframeService.getMainframe();
    }


    @RequestMapping({"health"})
    public @ResponseBody
    String serverHealth() {
        return "i am health";
    }

    @RequestMapping({"allServer"})
    public @ResponseBody
    InvigilatorDO getInvigilator() {
        return monitorService.getInvigilator();
    }

    @RequestMapping({"warningInfo"})
    public @ResponseBody
    WarningDO getServers() {
        return monitorService.getWarningInfo();
    }


}
