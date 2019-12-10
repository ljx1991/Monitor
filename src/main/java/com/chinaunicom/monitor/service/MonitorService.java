package com.chinaunicom.monitor.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.file.FileReader;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import com.chinaunicom.monitor.entity.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ljx
 */
public class MonitorService {

    /**
     * 读取配置文件，计算monitors配置的监控信息
     * @return
     */
    public InvigilatorDO getInvigilator() {
        JvmService jvmService = new JvmService();
        MainframeService mainframeService = new MainframeService();
        FileReader fileReader = new FileReader("monitorConfig.json");
        String monitorConfigStr = fileReader.readString();
        InvigilatorDO invigilator = JSONObject.parseObject(monitorConfigStr, InvigilatorDO.class);
        List<ServerDO> servers = invigilator.getServers();
        for (ServerDO server : servers) {
            List<MonitorDO> monitors = server.getMonitors();
            for (MonitorDO monitor : monitors) {
                if ("mainframe".equals(monitor.getType())) {
                    MainframeDO mainframe = null;
                    mainframe = mainframeService.getMainframe();
                    monitor.setMainframe(mainframe);
                } else if ("jvm".equals(monitor.getType())) {
                    JvmDO jvm = null;
                    jvm = jvmService.getJvm();
                    monitor.setJvm(jvm);
                }
            }
        }
        return invigilator;
    }


    public WarningDO getWarningInfo() {
        WarningDO warningDO = new WarningDO();
        List<WarningInfo> warningInfos = new ArrayList<>();
        DecimalFormat df = new DecimalFormat("0.00%");
        InvigilatorDO invigilator = this.getInvigilator();
        if (invigilator == null) {
            return null;
        }
        List<ServerDO> servers = invigilator.getServers();
        for (ServerDO server : servers) {
            WarningInfo warningInfo = new WarningInfo();
            String serverName = server.getServerName();
            String serviceName = "";
            switch (serverName) {
                case "order_center":
                    serviceName = "订单中心";
                    break;
                case "o2o":
                    serviceName = "统一门户线上线下一体化应用";
                    break;
                case "sysmanm":
                    serviceName = "统一门户系统管理应用";
                    break;
                default:
                    break;
            }
            String serverUri = server.getServerUri();
            String serverIp = serverUri.substring(7, serverUri.length() - 1);
            warningInfo.setServerName(serverName);
            warningInfo.setServiceName(serviceName);
            warningInfo.setServerIp(serverIp);
            List<MonitorDO> monitors = server.getMonitors();
            List<WarnItem> warningItems = new ArrayList<>();
            List<WarnItem> earlyWarningItems = new ArrayList<>();
            List<WarnItem> normalItems = new ArrayList<>();
            for (MonitorDO monitor : monitors) {
                if (StringUtils.equals(monitor.getType(), "jvm")) {
                    getJvmWarning(df, warningItems, earlyWarningItems, normalItems, monitor);
                }
                if (StringUtils.equals(monitor.getType(), "mainframe")) {
                    getCpuWarning(warningItems, earlyWarningItems, normalItems, monitor);
                    getDiskWarning(warningItems, earlyWarningItems, normalItems, monitor);
                    getMemoryWarning(df, warningItems, earlyWarningItems, normalItems, monitor);
                }
            }
            warningInfo.setEarlyWarningNum(earlyWarningItems.size());
            warningInfo.setWarningNum(warningItems.size());
            warningInfo.setNormalNum(normalItems.size());
            warningInfo.setWarningItems(warningItems);
            warningInfo.setEarlyWarningItems(earlyWarningItems);
            warningInfo.setNormalItems(normalItems);
            warningInfos.add(warningInfo);
        }
        warningDO.setWarningInfos(warningInfos);
        return warningDO;
    }

    /**
     * @param df                df
     * @param warningItems      获取的告警项
     * @param earlyWarningItems 获取的预警项
     * @param normalItems       获取的正常项
     * @param monitor           monitor
     */
    private void getMemoryWarning(DecimalFormat df, List<WarnItem> warningItems, List<WarnItem> earlyWarningItems, List<WarnItem> normalItems, MonitorDO monitor) {
        MainframeDO mainframe = monitor.getMainframe();
        MemoryInfo memoryInfo = mainframe.getMemoryInfo();
        BigDecimal memoryUsed = new BigDecimal(memoryInfo.getMemoryUsed());
        BigDecimal memoryTotal = new BigDecimal(memoryInfo.getMemoryTotal());
        BigDecimal divide = memoryUsed.divide(memoryTotal, 2, RoundingMode.HALF_UP);
        WarningType warningType = this.getWarningType(divide);
        WarnItem warnItem = new WarnItem();
        warnItem.setItemName("内存");
        warnItem.setItemValue(df.format(divide));
        if (warningType.getType() == -1) {
            warningItems.add(warnItem);
        }
        if (warningType.getType() == 0) {
            earlyWarningItems.add(warnItem);
        }
        if (warningType.getType() == 1) {
            normalItems.add(warnItem);
        }
    }

    /**
     * 获取磁盘的预警信息
     *
     * @param warningItems      获取的告警项
     * @param earlyWarningItems 获取的预警项
     * @param normalItems       获取的正常项
     * @param monitor           monitor
     */
    private void getDiskWarning(List<WarnItem> warningItems, List<WarnItem> earlyWarningItems, List<WarnItem> normalItems, MonitorDO monitor) {
        MainframeDO mainframe = monitor.getMainframe();
        List<DiskInfo> diskInfos = mainframe.getDiskInfos();
        for (DiskInfo diskInfo : diskInfos) {
            String diskUsageRate = diskInfo.getDiskUsageRate();
            BigDecimal diskUsageRateOfBigDecimal = this.getPercentToBigDecimal(diskUsageRate);
            WarningType warningType = this.getWarningType(diskUsageRateOfBigDecimal);
            WarnItem warnItem = new WarnItem();
            warnItem.setItemName("硬盘,挂载点:" + diskInfo.getDiskMount());
            warnItem.setItemValue(diskUsageRate);
            if (warningType.getType() == -1) {
                warningItems.add(warnItem);
            }
            if (warningType.getType() == 0) {
                earlyWarningItems.add(warnItem);
            }
            if (warningType.getType() == 1) {
                normalItems.add(warnItem);
            }
        }
    }

    /**
     * 获取cpu的预警信息
     *
     * @param warningItems      获取的告警项
     * @param earlyWarningItems 获取的预警项
     * @param normalItems       获取的正常项
     * @param monitor           monitor
     */
    private void getCpuWarning(List<WarnItem> warningItems, List<WarnItem> earlyWarningItems, List<WarnItem> normalItems, MonitorDO monitor) {
        MainframeDO mainframe = monitor.getMainframe();
        CpuInfo cpuInfo = mainframe.getCpuInfo();
        BigDecimal systemCpuLoad = this.getPercentToBigDecimal(cpuInfo.getSystemCpuLoad());
        WarningType warningType = this.getWarningType(systemCpuLoad);
        WarnItem warnItem = new WarnItem();
        warnItem.setItemName("cpu");
        warnItem.setItemValue(cpuInfo.getSystemCpuLoad());
        if (warningType.getType() == -1) {
            warningItems.add(warnItem);
        }
        if (warningType.getType() == 0) {
            earlyWarningItems.add(warnItem);
        }
        if (warningType.getType() == 1) {
            normalItems.add(warnItem);
        }
    }

    /**
     * 获取jvm的预警信息
     *
     * @param df                格式化成
     * @param warningItems      获取的告警项
     * @param earlyWarningItems 获取的预警项
     * @param monitor           monitor
     */
    private void getJvmWarning(DecimalFormat df, List<WarnItem> warningItems, List<WarnItem> earlyWarningItems, List<WarnItem> normalItems, MonitorDO monitor) {
        JvmDO jvm = monitor.getJvm();
        List<JvmMemoryInfo> jvmMemoryInfos = jvm.getJvmMemoryInfos();
        for (JvmMemoryInfo jvmMemoryInfo : jvmMemoryInfos) {
            BigDecimal usedMemory = new BigDecimal(jvmMemoryInfo.getUsedMemory());
            BigDecimal maxMemory = new BigDecimal(jvmMemoryInfo.getMaxMemory());
            BigDecimal divide = usedMemory.divide(maxMemory, 2, RoundingMode.HALF_UP);
            WarningType warningType = getWarningType(divide);
            WarnItem warnItem = new WarnItem();
            warnItem.setItemName("jvm " + jvmMemoryInfo.getMemoryType() + " 内存");
            warnItem.setItemValue(df.format(divide));
            if (warningType.getType() == -1) {
                warningItems.add(warnItem);
            }
            if (warningType.getType() == 0) {
                earlyWarningItems.add(warnItem);
            }
            if (warningType.getType() == 1) {
                normalItems.add(warnItem);
            }
        }
    }

    /**
     * @param divide
     * @return
     */
    private WarningType getWarningType(BigDecimal divide) {
        if ((divide.compareTo(BigDecimal.valueOf(0.9)) >= 0)) {
            return WarningType.WARNING;
        } else if (divide.compareTo(BigDecimal.valueOf(0.7)) >= 0) {
            return WarningType.EARLY_WARNING;
        }
        return WarningType.NORMAL;
    }

    /**
     * 将百分比转为BigDecimal
     *
     * @param percent
     */
    private BigDecimal getPercentToBigDecimal(String percent) {
        percent = percent.replace("%", "");

        BigDecimal bigDecimal = null;
        try {
            float f = Float.parseFloat(percent) / 100;
            bigDecimal = new BigDecimal(f);
        } catch (Exception e) {
            return BigDecimal.ZERO;
        }
        return bigDecimal;
    }


}
