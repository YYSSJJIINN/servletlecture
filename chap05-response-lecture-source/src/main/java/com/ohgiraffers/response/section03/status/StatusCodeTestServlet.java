package com.ohgiraffers.response.section03.status;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/status")
public class StatusCodeTestServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        
        // HttpServletResponse의 sendError() 메서드를 사용할 수 있다.
        // https://developer.mozilla.org/ko/docs/Web/HTTP/Status
//        resp.sendError(404, "없는 페이지입니다(내가 할 줄 모르는 일임). 경로를 확인해주세요. 고객님 잘못입니다.");
//        resp.sendError(500, "서버 내부 오류입니다. 서버 오류는 개발자의 잘못이고, 곧 개발자는 여러분입니다...");
        resp.sendError(999, "그냥 내가 하고싶은 말 해볼래.");

    }
}
