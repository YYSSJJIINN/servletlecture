package com.ohgiraffers.filter.section02.uses;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;

@WebFilter("/member/*")
public class PasswordEncryptFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("[PasswordEncryptFilter](init) 호출됨...");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        System.out.println("[PasswordEncryptFilter](doFilter) 호출됨...");

        /* 원본 request 객체를 우리가 재정의한 request 객체로 교체해서 다음 서블릿(또는 필터)로 전달
        *  요청받은 정보로부터 값을 꺼내올 때, 원본 request 객체의 원본 비밀번호가 아닌
        *  조작된 request 객체의 암호화된 비밀번호를 꺼낼 수 있도록 wrapper를 이용한다.
        * */
        // 1단계.원본 객체인 노란색 req를 2단계.감싼 라임색이 전달되어 결국엔 3단계.라임색의 wrapper가 이용된다.
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        RequestWrapper wrapper = new RequestWrapper(httpRequest);

        chain.doFilter(wrapper, response);
    }

    @Override
    public void destroy() {
        System.out.println("[PasswordEncryptFilter](destroy) 호출됨...");
    }
}
