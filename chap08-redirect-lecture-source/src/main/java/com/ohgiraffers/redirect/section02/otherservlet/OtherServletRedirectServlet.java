package com.ohgiraffers.redirect.section02.otherservlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

// 1차 서블릿
@WebServlet("/otherservlet")
public class OtherServletRedirectServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        System.out.println("1차 서블릿 GET 요청 수락!");
        System.out.println("1차 서블릿의 resp 객체 = " + resp);

        /* 리다이렉트 요청을 받는 2차 서블릿측의 getAttribute로 값을 추출해보면 null값이 추출된다.
        *  왜냐하면 redirect 구조에서는 1차 서블릿이 브라우저에게 redirect하라는 301/302 상태 코드를 응답하는 순간
        *  해당 doGet() 메서드는 소멸하게 되고, 자연스레 전달인자인 request 및 response 객체도 소멸된다.
        *  따라서 redirect 구조에서는 request와 response 객체를 이용해서 2차 서블릿에게 데이터를 공유해줄 수 없다.*/
        req.setAttribute("todaysLunch", "soup");

        resp.sendRedirect("redirect");  // 보낼 주소는 2차 서블릿의 리다이렉트 주소.
//        resp.sendRedirect("redirect?todaysLunch=soup");     // 꼼수버전
    }
}
