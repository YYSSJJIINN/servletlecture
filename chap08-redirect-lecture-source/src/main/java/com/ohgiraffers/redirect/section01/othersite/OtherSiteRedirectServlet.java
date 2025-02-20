package com.ohgiraffers.redirect.section01.othersite;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/othersite")
public class OtherSiteRedirectServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        System.out.println("GET /othersite 요청 접수! NAVER로 리다이렉트 해드리겠습니다!");

        // Servlet은 요청에 대해 다른 곳으로 가라고 응답 해줘야하므로 resp(response) 사용
        // 301/302번대 코드가 나오며 새로운 주소(URL)가 주어지고 그 곳으로 강제이동해야한다.
        /* 해당 요청은 브라우저의 개발자도구(F12) 네트워크 탭에서 모니터링 해볼 수 있다.
        *  Network 탭을 보면 302 응답 코드와 함게 NAVER 홈페이지로 이동하는 것을 볼 수 있다.
        *  '사용자 URL 재작성'이라고 부르는 redirect 방식은 302번 응답 코드인 경우 요청에 대한 처리를 완료하였고
        *  사용자의 URL을 강제로 redirect 경로로 이동시키라는 의미다.
        *   응답 헤더 작성은 'General Header의 302번 코드 ' + 'Response Header의 Location 헤더 값에 경로' 조합으로
        *  작성해서 응답하면 된다.
        * */
        resp.sendRedirect("https://www.naver.com");
    }
}
