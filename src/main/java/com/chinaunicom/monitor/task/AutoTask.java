package com.chinaunicom.monitor.task;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.EscapeUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.JSONToken;
import com.chinaunicom.log.LoggerHelp;
import com.chinaunicom.monitor.entity.InvigilatorDO;
import com.chinaunicom.monitor.entity.WarningDO;
import com.chinaunicom.monitor.service.JvmService;
import com.chinaunicom.monitor.service.MainframeService;
import com.chinaunicom.monitor.service.MonitorService;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;

/**
 * @author ljx
 */
public class AutoTask {

//    @Autowired
//    private MonitorService monitorService;

    public void checkLine() {
        MonitorService monitorService = new MonitorService();
        WarningDO warningInfo = monitorService.getWarningInfo();
        String warningInfoStr = JSON.toJSONString(warningInfo);
        //我们的任务执行体
        System.out.println("warningInfo" + warningInfoStr);
//        System.out.println(StringEscapeUtils.escapeJson(warningInfoStr));
        LoggerHelp.info(StringEscapeUtils.escapeJson(warningInfoStr));

//        DateTime now = DateTime.now();
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("metricsSystem","应用监控");
//        jsonObject.put("metricsType",2);
//        jsonObject.put("metricsKey","host");
//        jsonObject.put("metricsValue",1);
//        jsonObject.put("metricsTime",now.toString());
//        jsonObject.put("metricsResult",warningInfoStr);
//        jsonObject.put("createdBy","application");
//
//        String url = "http://10.32.7.177:9002/monitor/metrics";
//        String json = jsonObject.toString();
//        System.out.println(json);
//        String result2 = HttpRequest.post(url)
//                .body(json)
//                .execute().body();
//        System.out.println(result2);
    }

    public void checkClient() {
        //我们的任务执行体
        System.out.println("执行任务2");
    }
}
