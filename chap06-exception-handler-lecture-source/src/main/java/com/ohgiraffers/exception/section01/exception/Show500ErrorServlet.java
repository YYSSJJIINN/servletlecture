package com.ohgiraffers.exception.section01.exception;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/show500error")
public class Show500ErrorServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        System.out.println("/show500error 서블릿 호출됨...");
        resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "인터널 서버 에러");
//        resp.sendError(500,"인터널 서버 에러");
    }
}
