package com.ohgiraffers.response.section02.headers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;

@WebServlet("/headers")
public class ResponseHeaderPrintServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        /* 서버의 현재 시간을 동적인 페이지로 만들어서 클라이언트에게 보내보자. */

        // MIME 타입을 설정
        resp.setContentType("text/html");
        System.out.println("MIME 타입 : " + resp.getContentType());
        System.out.println("인코딩 타입 : " + resp.getCharacterEncoding());

        // 현재 시간을 생성
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.KOREA);
        String currentDatetime = sdf.format(new Date());
        System.out.println("현재 서버의 시간 : " + currentDatetime);

        /* (예제) 라이브 시계 만들기
        * HTTP 응답 헤더에 "Refresh"라는 헤더를 추가하고, 그 값을 "1" 로 설정한다.
        *  그러면 클라이언트(브라우저)는 일정 시간이 지날 때 마다 페이지를 자동으로 새로고침 하게 된다.
        *  실시간 주식 가격, 라이브 경기 점수 등 사용자에게 일정 시간마다 자동으로 갱신되어야 하는 정보를
        *  제공하는 페이지에서 유용하게 사용될 수 있다.
        *  다만, 새로 고침 간격이 너무 짧으면 서버에 무리하게 요청하여 부담을 주게 되니
        *  특별한 경우가 아니라면 사용하지 말아야 한다.
        *  (새로 고침하는 책임을 클라이언트가 떠안게 해야 한다) */
//        resp.setHeader("Refresh", "1");

        // 스트림 생성
        PrintWriter out = resp.getWriter();

        // 문자열 조립 및 출력
        out.print("<h1>" + currentDatetime + "</h1>");

        // 스트림 수거
        out.flush();
        out.close();

        System.out.println("---------- Response Header K-V ----------");
        Collection<String> respHeaders = resp.getHeaderNames();
        Iterator<String> iter = respHeaders.iterator();
        while (iter.hasNext()) {
            String headerName = iter.next();
            System.out.println(headerName + " " + resp.getHeader(headerName));
        }
        System.out.println("-----------------------------------------");
    }
}
