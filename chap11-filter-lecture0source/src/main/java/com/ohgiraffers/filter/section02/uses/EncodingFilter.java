package com.ohgiraffers.filter.section02.uses;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;

public class EncodingFilter implements Filter {

    private String encodingType;        // 인코딩 형식

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("[EncodingFilter](init) 호출됨...");

        // XML에 설정한 init-param의 key값을 이용해 전달인자로 전달된 filterConfig에서 값을 꺼내올 수 있음.
        // Tomcat v10부터는 하지 않아도 될 작업임.
        // encodingType에는 xml파일의 charset=UTF-8이 담긴다.
        encodingType = filterConfig.getInitParameter("initEncodingType");
        System.out.println("XML에 정의한 초기 인코딩 타입값 : " + encodingType);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        System.out.println("[EncodingFilter](doFilter) 호출됨...");

        // 전달인자로 받은 ServletRequest를 HttpServletRequest로 강제형변환
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        /* 현재 요청이 POST 요청일 때, 인코딩 설정을 UTF-8로 사전 처리(전처리)해주고 다음 서블릿(또는 필터)로 넘긴다.
        *  단, Tomcat v10 이상일 때는 기본 인코딩 설정값이 UTF-8이기 때문에 사실상 해당 필터 자체가 필요 없다.
        * */
        if("POST".equals(httpRequest.getMethod())) {
            request.setCharacterEncoding("UTF-8");
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        System.out.println("[EncodingFilter](destroy) 호출됨...");
    }
}
