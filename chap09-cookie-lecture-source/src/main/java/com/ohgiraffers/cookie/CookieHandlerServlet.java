package com.ohgiraffers.cookie;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/cookie")
public class CookieHandlerServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("POST /cookie 서블릿 호출됨...");

        /* 1. 사용자의 입력값 추출 */
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");

        System.out.println("firstName = " + firstName);
        System.out.println("lastName = " + lastName);

        /* 2. 비즈니스 로직 처리했다 가정. */

        /* 필기. 쿠키를 사용하는 방법은 간단하며, 쿠키를 사용하는 절차는 다음과 같다. */
        /*  1. 쿠키를 생성한다.
         *  2. 생성한 쿠키의 만료 시간을 설정한다.
         *  3. 응답 헤더에 쿠키를 담는다.
         *  4. 응답을 보낸다.
         *  =======================================================================================
         *  쿠키는 사전에 고려해둬야 할 일부 제약 사항이 존재한다:
         *  쿠키의 이름은 ascii 문자만을 사용해야 하며 한 번 설정한 쿠키의 이름은 변경할 수 없다.
         *  또한 쿠키의 이름에는 공백문자와 일부 특수문자([ ] ( ) = , " \ ? @ : ;)를 사용할 수 없다.
         * */

        /* 3. 쿠키 생성 */
        /* 3-1. Cookie는 기본 생성자를 지원하지 않는다.
        *  매개변수 있는 생성자로 쿠키를 생성할 수 있다.
        * */
        Cookie firstNameCookie = new Cookie("firstName", firstName);
        Cookie lastNameCookie = new Cookie("lastName", lastName);

        /* 3-2. 쿠키 만료 시간 설정 */
        firstNameCookie.setMaxAge(60 * 60 * 24);    // 전달인자는 초단위 설정이다.
        lastNameCookie.setMaxAge(60 * 60 * 24);

        /* 3-3. 응답 헤더에 쿠키 등록 */
        resp.addCookie(firstNameCookie);
        resp.addCookie(lastNameCookie);

        /* 4. 응답 전송 */
        resp.sendRedirect("redirect");
    }
}
