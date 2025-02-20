package com.ohgiraffers.redirect.section02.otherservlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

// 2차 서블릿
@WebServlet("/redirect")
public class RedirectServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        System.out.println("2차 서블릿 리다이렉트 잘 받았습니다!");
        System.out.println("2차 서블릿의 resp = " + resp);   // 1차 서블릿과 다른 주소값이 출력됨(실제 다르니까)

        System.out.println("오늘의 점심 메뉴는 : " + req.getAttribute("todaysLunch"));  // null

        // 리다이렉트가 성공했을 때 어떤 페이지를 조립할 것인지.
        StringBuilder redirectText = new StringBuilder();
        redirectText.append("<!doctype html>\n")
                .append("<head>\n")
                .append("</head>\n")
                .append("<body>\n")
                .append("<h1>이 서블릿으로 redirect 성공!</h1>")
                .append("<p>점심메뉴 : " + req.getAttribute("todaysLunch") + "</p>")
//                .append("<p>점심메뉴 : " + req.getParameter("todaysLunch") + "</p>")    // 꼼수버전
                .append("</body>\n")
                .append("</html>\n");

        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();

        out.print(redirectText);

        out.flush();
        out.close();

        /* Forward vs. Redirect
        *  - request 또는 response 객체를 유지시켜서 요청을 처리하고자 한다면 forward를 사용할 것.
        *  - 클라이언트(브라우저)가 페이지를 새로고침(재요청) 할 때마다 처음 요청하는 서블릿을
        *    다시 요청하는 상황(특히 DB DML 작업)을 방지하고자 한다면 redirect를 사용할 것.
        * */
        /* 그렇다면 redirect 시 값을 유지해야 하는 공유 데이터가 있다면 방법이 무엇일까?
        *  크게 쿠키(cookie)와 세션(session)이라는 기술을 이용할 수 있다.
        * */
    }
}
