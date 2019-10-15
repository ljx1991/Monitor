package com.chinaunicom.monitor.service;

import com.chinaunicom.monitor.entity.*;
import org.springframework.stereotype.Service;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.GlobalMemory;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.hardware.NetworkIF;
import oshi.software.os.FileSystem;
import oshi.software.os.OSFileStore;
import oshi.software.os.OperatingSystem;

import java.util.ArrayList;
import java.util.List;


/**
 * @author ljx
 */
@Service
public class MainframeService {


    private static SystemInfo systemInfo = new SystemInfo();

    public MainframeDO getMainframe() {
        MainframeDO mainframe = new MainframeDO();
        mainframe.setCpuInfo(getCpuInfo());
        mainframe.setDiskInfos(getDiskInfos());
        mainframe.setMemoryInfo(getMemoryInfo());
        mainframe.setNetworkInfos(getNetworkInfos());
        mainframe.setOperatingSystemInfo(getOperatingSystemInfo());
        return mainframe;
    }


    /**
     * 获取操作系统信息
     *
     * @return
     */
    public OperatingSystemInfo getOperatingSystemInfo() {
        OperatingSystem operatingSystem = systemInfo.getOperatingSystem();
        OperatingSystemInfo operatingSystemInfo = new OperatingSystemInfo();
        operatingSystemInfo.setOperatingSystemName(operatingSystem.getFamily());
        operatingSystemInfo.setOperatingSystemVersion(operatingSystem.getVersion().toString());
        return operatingSystemInfo;
    }

    /**
     * 获取内存信息
     *
     * @return
     */
    public MemoryInfo getMemoryInfo() {
        MemoryInfo memoryInfo = new MemoryInfo();
        HardwareAbstractionLayer hardware = systemInfo.getHardware();
        GlobalMemory memory = hardware.getMemory();
        memoryInfo.setMemoryTotal(String.valueOf(memory.getTotal()));
        memoryInfo.setMemoryUsed(String.valueOf(memory.getTotal() - memory.getAvailable()));
        memoryInfo.setMemoryAvailable(String.valueOf(memory.getAvailable()));
        memoryInfo.setSwapTotal(String.valueOf(memory.getSwapTotal()));
        memoryInfo.setSwapUsed(String.valueOf(memory.getSwapUsed()));
        memoryInfo.setSwapAvailable(String.valueOf(memory.getSwapTotal() - memory.getSwapUsed()));
        return memoryInfo;
    }

    /**
     * 获取cpu信息
     *
     * @return
     */
    public CpuInfo getCpuInfo() {
        CpuInfo cpuInfo = new CpuInfo();
        HardwareAbstractionLayer hardware = systemInfo.getHardware();
        CentralProcessor processor = hardware.getProcessor();
        cpuInfo.setPhysicalCpuCore(processor.getPhysicalProcessorCount());
        cpuInfo.setLogicalCpuCore(processor.getLogicalProcessorCount());
        cpuInfo.setCpuUsage(String.format("%.2f%%", processor.getSystemCpuLoadBetweenTicks() * 100));
        cpuInfo.setSystemCpuLoad(String.format("%.2f%%", processor.getSystemCpuLoad() * 100));
        return cpuInfo;
    }

    /**
     * 获取硬盘信息
     *
     * @return
     */
    public List<DiskInfo> getDiskInfos() {
        List<DiskInfo> diskInfos = new ArrayList();
        OperatingSystem operatingSystem = systemInfo.getOperatingSystem();
        FileSystem fileSystem = operatingSystem.getFileSystem();
        OSFileStore[] fsArray = fileSystem.getFileStores();
        for (OSFileStore fs : fsArray) {
            DiskInfo diskInfo = new DiskInfo();
            long usable = fs.getUsableSpace();
            long total = fs.getTotalSpace();
            diskInfo.setDiskSize(String.valueOf(total));
            diskInfo.setDiskUsedSize(String.valueOf(usable));
            diskInfo.setDiskUsageRate(String.format("%.2f%%", 100d * usable / total));
            diskInfo.setDiskMount(fs.getMount());
            diskInfos.add(diskInfo);
        }
        return diskInfos;
    }


    /**
     * 获取网络信息
     * @return
     */
    public List<NetworkInfo> getNetworkInfos() {
        List<NetworkInfo> networkInfos = new ArrayList();
        HardwareAbstractionLayer hardware = systemInfo.getHardware();
        NetworkIF[] networkIFs = hardware.getNetworkIFs();
        for (NetworkIF net : networkIFs) {
            NetworkInfo networkInfo = new NetworkInfo();
            String[] iPv4addrArray = net.getIPv4addr();
            if (iPv4addrArray == null || iPv4addrArray.length < 1) {
                continue;
            }
            networkInfo.setIpV4(iPv4addrArray[0]);
//            networkInfo.setIpV6(Arrays.toString(net.getIPv6addr()));
            networkInfo.setNetName(net.getName());
            networkInfos.add(networkInfo);
        }
        return networkInfos;
    }


}