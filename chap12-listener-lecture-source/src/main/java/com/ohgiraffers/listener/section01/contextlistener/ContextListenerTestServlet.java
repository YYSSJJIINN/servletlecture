package com.ohgiraffers.listener.section01.contextlistener;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/context")
public class ContextListenerTestServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        System.out.println("[ContextListenerTestServlet] doGet() 호출됨...");

        /* 컨텍스트 생성 */
        ServletContext context = req.getServletContext();

        /* 컨텍스트에 어틜뷰트 추가 */
        context.setAttribute("test", "value");

        /* 컨텍스트에 추가했던 어트리뷰트 값 수정 */
        context.setAttribute("test", "value2");

        /* 컨텍스트에 추가했던 어트리뷰트 삭제 */
        context.removeAttribute("test");
    }
}
