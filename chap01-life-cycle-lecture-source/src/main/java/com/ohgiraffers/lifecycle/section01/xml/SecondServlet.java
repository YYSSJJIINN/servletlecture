package com.ohgiraffers.lifecycle.section01.xml;

import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServlet;

import java.io.IOException;

public class SecondServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
        System.out.println("두번째 서블릿의 이닛");
    }

    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        System.out.println("두번째 서블릿의 서비스 진행");
    }
    @Override
    public void destroy() {
        System.out.println("두번재 서블릿의 디스트로이");
    }
}
