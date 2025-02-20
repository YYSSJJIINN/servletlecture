package com.ohgiraffers.forward.section01.forward;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/print")
public class PrintLoginSuccessServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        System.out.println("POST / forward 요청으로부터 포워딩 된 POST /print 입니다");

        // 앞선 서블릿에서 추출했던 파라미터 값을 포워딩된 서블릿에서도 추출해보기.
        String userId = req.getParameter("userId");
        String password = req.getParameter("password");

        System.out.println("userId = " + userId);
        System.out.println("password = " + password);

        // 앞선 서블릿에서 공유 목적으로 할당한 어트리뷰트값 추출해보기.
        // getAttribute라는 object로 요청해서 반환값도 object이므로 toString()를 사용해서 변환한다.
        String userName = req.getAttribute("userName").toString();  // Object -> String
        int userAge = (int) req.getAttribute("userAge");            // Object -> int

        System.out.println("userName = " + userName);
        System.out.println("userAge = " + userAge);

        /* 필기. 응답에 필요한 데이터가 준비되면 동적인 웹 페이지를 생성한다. */
        StringBuilder responseText = new StringBuilder();
        responseText.append("<!doctype html>\n")
                .append("<html>\n")
                .append("<head>\n")
                .append("</head>\n")
                .append("<body>\n")
                .append("<h3 align=\"center\">")
                .append(userAge + "세 " + userName + "(" + userId + ")")
                .append("님 환영합니다.</h3>")
                .append("</body>\n")
                .append("</html>\n");

        resp.setContentType("text/html");

        PrintWriter out = resp.getWriter();

        out.print(responseText);
        out.flush();
        out.close();
        /* 필기.
         *  변수의 디폴트 스코프는 메소드(= 해당 페이지) 스코프이기 때문에 다른 클래스(= 서블릿)와 데이터를 공유할 수 없다.
         *  하지만 forward 방식은 request 및 response 객체를 포함하여 위임하므로 request에 정보를 저장하여 forward하면
         *  위임받은 서블릿에서도 위임한 서블릿의 정보를 공유할 수 있다.
         *  forward 받은 서블릿의 존재를 클라이언트가 알 필요는 없기 때문에 url자체는 변경되지 않는다.
         *  (사용자는 결과 화면만 제대로 받으면 되기 때문이다.)
         *  forward 방식의 또 다른 특징은 요청 시 서버로 전송한 데이터가 남아있는 상태로 새로고침(= 재요청)하면
         *  동일한 요청을 반복하게 되는데, 만약 데이터베이스에 INSERT하는 등의 행위를 하면 의도치 않게 중복된 행이 삽입될 가능성이 있다.
         *  따라서 그런 경우는 다른 페이지 전환 방식인 sendRedirect를 이용한다 => 프로젝트를 새롭게 만들고 테스트 해보자.
         * */
    }
}
