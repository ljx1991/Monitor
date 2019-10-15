package com.chinaunicom.monitor.filter;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.io.file.FileReader;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

public class AuthorityFilter implements Filter {

    private List<String> whiteList;


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        FileReader fileReader = new FileReader("authority.json");
        String authorityConfigStr = fileReader.readString();
        JSONObject authorityObject = JSONObject.parseObject(authorityConfigStr);
        JSONArray white = authorityObject.getJSONArray("whiteList");
        whiteList = white.toJavaList(String.class);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String remoteHost = servletRequest.getRemoteHost();
        if (StringUtils.equals(remoteHost,"0:0:0:0:0:0:0:1")) {
            filterChain.doFilter(servletRequest, servletResponse);
        }
        if (CollectionUtil.contains(whiteList,remoteHost)) {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {

    }
}
