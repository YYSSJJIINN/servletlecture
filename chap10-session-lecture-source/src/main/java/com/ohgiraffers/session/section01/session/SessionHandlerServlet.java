package com.ohgiraffers.session.section01.session;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/session")
public class SessionHandlerServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        System.out.println("POST /session 서블릿 호출됨...");

        /* 1. 사용자 입력값 추출 */
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");

        System.out.println("firstName = " + firstName);
        System.out.println("lastName = " + lastName);

        /* 2. 세션 생성 */
        // request 객체의 getSession() 메서드로 세션 객체 생성
        HttpSession session = req.getSession();

        // 세션 만료 시간 설정(10분)
        System.out.println("디폴트 세션 만료 시간 : " + session.getMaxInactiveInterval());   // 30분
        session.setMaxInactiveInterval(60 * 10);
        System.out.println("변경된 세션 만료 시간 : " + session.getMaxInactiveInterval());   // 10분

        // 세션 ID 확보
        System.out.println("SESSION ID : " + session.getId());

        // 세션에 데이터 저장
        session.setAttribute("firstName", firstName);
        session.setAttribute("lastName", lastName);

        /* 3. 리다이렉트 */
        resp.sendRedirect("redirect");
    }
}
