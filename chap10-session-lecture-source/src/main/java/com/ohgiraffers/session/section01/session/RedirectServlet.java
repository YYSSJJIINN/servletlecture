package com.ohgiraffers.session.section01.session;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

@WebServlet("/redirect")
public class RedirectServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        System.out.println("GET /redirect 서블렛 리다이렉트됨...");

        // 세션 생성
        HttpSession session = req.getSession();

        // 세션 ID 확인
        System.out.println("리다이렉트된 서블릿에서 확인해보는 세션 ID : " + session.getId());

        // 세션 객체에 담겨있는 모든 어트리뷰트 키값 확인
        Enumeration<String> sessionNames = session.getAttributeNames();
        while (sessionNames.hasMoreElements()) {
            System.out.println(sessionNames.nextElement());
        }

        // 세션에서 값 추출(단, 동일한 session id를 갖는 세션에 한해서)
        // object인 것을 String으로 변환
        String firstName = (String) session.getAttribute("firstName");  // 강제형변환
        String lastName = session.getAttribute("firstName").toString(); // Object.toString()메서드 사용

        // 응답 생성
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();

        /* 설명. StringBuilder 대신 PrintWriter의 println() 함수를 사용해 출력할 수도 있다. */
        out.println("<!doctype html>");
        out.println("<html>");
        out.println("<head>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h3>Your first name is ");
        out.println(firstName);
        out.println(" and last name is ");
        out.println(lastName);
        out.println("</h3>");
        out.println("</body>");
        out.println("</html>");

        out.flush();
        out.close();

        /* 클라이언트로부터 서버의 서블릿으로 요청이 들어올 때,
        *  HttpServletRequest의 Header에는 JSESSIONID라는 값이 같이 포함되어 넘어온다.
        * (이 JSESSIONID는 클라이어늩와 서버간의 세션을 식별하는데 사용되는 고유식별자로 중복되지 않는 값이다.)
        *  그러면 서블릿(= 서버 = 톰캣)은 이 JSESSIONID에 해당되는 HttpSesion 객체를 검색한다.
        *  이 때, 서버에 이와 매칭되는 세션 객체가 이미 존재한다면 기존 세션 객체를 재사용하고, 세션 객체가 없을 때만 새로 생성한다.
        *  (일반적으로 요청 헤더에 JSESSIONID가 없는 경우 서블릿 컨테이너는 자동으로 HttpSession 객체를 생성하고
        *  새로운 JSESSIONID를 발급해 클라이언트에게 반환한다.)
        *  이제야 서버는 사용자별로 데이터를 저장하고 관리할 수 있는 전용 공간을 제공할 수 있게 되었으며
        *  이 공간을 사용해 사용자는 웹앱과 상호작용하는 동안 일관된 사용자 경험을 제공해줄 수 있다.
        * */
    }
}
