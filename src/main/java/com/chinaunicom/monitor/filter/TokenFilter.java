package com.chinaunicom.monitor.filter;

import cn.hutool.core.date.DateUtil;
import cn.hutool.crypto.SecureUtil;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author ljx
 */
public class TokenFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest= (HttpServletRequest)servletRequest;
        String token = httpServletRequest.getParameter("token");
        String datetime = DateUtil.format(DateUtil.date(), "yyyy-MM-dd HH");
        System.out.println(datetime);
        System.out.println(SecureUtil.md5(datetime));
        if (StringUtils.equals(SecureUtil.md5(datetime),token)) {
            filterChain.doFilter(servletRequest, servletResponse);
        }else {
            throw new ServletException("您没有权限访问！");
        }
    }

    @Override
    public void destroy() {

    }
}
