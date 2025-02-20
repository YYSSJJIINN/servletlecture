package com.ohgiraffers.session.section02.delete;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Enumeration;

@WebServlet("/delete")
public class DeleteSessionDataServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        System.out.println("GET /delete 세션 삭제(무효화)하는 서블릿 호출됨...");

        // 세션 생성
        HttpSession session = req.getSession();

        // 세션 ID 확인
        System.out.println("세션 삭제 전 서블릿에서 확인해보는 세션 ID : " + session.getId());

        // 세션 객체에 담겨있는 모든 어트리뷰트 키값 확인
        Enumeration<String> sessionNames = session.getAttributeNames();
        while (sessionNames.hasMoreElements()) {
            System.out.println(sessionNames.nextElement());
        }

        /* 세션 데이터를 사젷나는 방법은 여러가지가 있다.
        *  1. 설정한 만료시간이 모두 지나면 세션이 만료됨
        *  2. removeAttribute() 메서드로 session에 할당된 어트리뷰트를 삭제한다.
        *  3. invalidate() 메서드로 세션의 모든 데이터를 제거한다.
        * */

        /* HttpSession의 removeAttribute() 메서드 사용 */
        System.out.println("--------------- 세션에서 어트리뷰트 값 삭제 ---------------");
        session.removeAttribute("firstName");   // 세션의 firstName 어트리뷰트를 삭제

        sessionNames = session.getAttributeNames();
        while (sessionNames.hasMoreElements()) {
            System.out.println(sessionNames.nextElement());
        }

        /* HttpSession의 invalidate() 메서드 사용 */
        System.out.println("--------------- 세션 무효화 ---------------");
        session.invalidate();

        /* invalidate() 메서드를 사용하면 세션 자체를 무효화 시키기 때문에 이후 세션을 사용하는 코드는 에러가 발생한다.
        *  즉, 세션 내 데이터를 삭제하는 수준이 아니라 세션 객체를 강제로 만료시키는 작업이다.
        * */
        sessionNames = session.getAttributeNames();
        while (sessionNames.hasMoreElements()) {
            System.out.println(sessionNames.nextElement());
        }
    }
}
