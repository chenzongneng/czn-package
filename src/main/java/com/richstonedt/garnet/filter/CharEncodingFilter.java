package com.richstonedt.garnet.filter;

import javax.servlet.*;
import java.io.IOException;

public class CharEncodingFilter implements Filter {

    private String encoding;

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
//        System.out.println(encoding);
        request.setCharacterEncoding("UTF-8");
        chain.doFilter(request, response);
//        System.out.println("end filter");
    }

    @Override
    public void init(FilterConfig cfg) throws ServletException {
        String e = cfg.getInitParameter("enconding");
        if (e == null || "".equals(e.trim())) {
            encoding = "UTF-8";
        } else {
            encoding = e;
        }
    }
}
