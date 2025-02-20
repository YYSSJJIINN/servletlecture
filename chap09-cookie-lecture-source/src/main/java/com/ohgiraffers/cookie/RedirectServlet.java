package com.ohgiraffers.cookie;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/redirect")
public class RedirectServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        System.out.println("GET /redirect 서블릿 호출됨...");

        /* 1. request 객체에서 쿠키 추출 */
        Cookie[] cookies = req.getCookies();

//        for(int i = 0; i < cookies.length; i++) {
//            System.out.println(cookies[i].getName() + " : " + cookies[i].getValue());
//        }

        /* 2. 쿠키 속 데이터 추출 */
        String firstName = "";
        String lastName = "";

        for(Cookie c :cookies) {
            if(c.getName().equals("firstName")) {
                firstName = c.getValue();
            } else if(c.getName().equals("lastName")) {
                lastName = c.getValue();
            }
        }

        System.out.println("firstName 쿠키 값= " + firstName);
        System.out.println("lastName 쿠키 값= " + lastName);

        /* 3. 응답 작성 및 전송 */
        StringBuilder responseText = new StringBuilder();
        responseText.append("<!doctype html>\n")
                .append("<html>\n")
                .append("<head>\n")
                .append("</head>\n")
                .append("<body>\n")
                .append("<h3>your first name is ")
                .append(firstName)
                .append(", and last name is ")
                .append(lastName)
                .append(".")
                .append("</h3>")
                .append("</body>\n")
                .append("</html>\n");

        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();

        out.print(responseText);

        out.flush();
        out.close();

        /* 필기. 쿠키는 텍스트 파일 형태로 클라이언트 컴퓨터에 저장된다.
         *  따라서 개인 PC는 크게 상관 없지만, 공용 PC 등 다른 사용자와 함께 쓰는 컴퓨터일 경우,
         *  즉 민감한 개인정보를 포함된 경우에는 보안에 취약하다.
         *  이렇게 민감한 개인 정보를 취급하는 경우에는 쿠키보다는 세션을 이용한다.
         *  세션은 쿠키와 유사한 형태로 K-V 쌍으로 저장되지만, 서버(톰캣)에서 관리되므로
         *  보안 측면에서는 상대적으로 더 우수하다는 장점을 가진다.
         *  (단, 클아이언트 수가 많아진다면 세션 정보도 많아지므로 서버 측 부담이 증가할 수 있다)
         * */
    }
}
