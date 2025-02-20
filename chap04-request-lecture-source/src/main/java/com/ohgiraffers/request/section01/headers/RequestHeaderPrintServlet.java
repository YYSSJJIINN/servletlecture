package com.ohgiraffers.request.section01.headers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Enumeration;

@WebServlet("/headers")
public class RequestHeaderPrintServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        /* 설명. 요청 시 전달되는 헤더라는 것이 어떤 정보들을 포함하고 있는지 확인해보자
         * 필기. 헤더의 종류는 전통적으로 4가지 카테고리로 구분된다.
         *  1. General header : 요청 및 응답 모두에 적용되지만 최종적으로는 body에 전송되는 것과 관련이 없는 헤더이다.
         *  2. Request header : Fetch될 리소스나 클라이언트 자체에 대한 상세 정보를 포함하는 헤더이다.
         *  3. Response header : 위치나 서버 자체와 같은 응답에 대한 부가적인 정보를 갖는 헤더이다.
         *  4. Entity header : 컨텐츠 길이나 MIME타입과 같은 엔티티 body에 대한 상세 정보를 포함하는 헤더이다.
         *                     (요청 응답 모두 사용되며, 메시지 바디의 컨텐츠를 나타내기에 GET요청은 해당하지 않는다.)
         *                     (Content_Length, Content-Type, Content-Language, Content-Encoding)
         *
         * 설명. 출력해서 나오는 것들은 요청헤더(Request Header)이다.
         *  accept : 요청을 보낼 때 서버에게 요청할 응답 타입 명시
         *  accept-encoding : 응답 시 원하는 인코딩 방식
         *  accept-language : 응답 시 원하는 언어
         *  connection : HTTP 통신이 완료된 후에 네트워크 접속을 유지할지 결정 (기본값: keep-alive = 연결을 열린 상태로 유지)
         *  host : 서버의 도메인 네임과 서버가 현재 Listening 중인 TCP포트 지정 (반드시 하나가 존재. 없거나 둘 이상이면 404)
         *  referer : 이 페이지 이전에 대한 주소
         *  sec-fetch-dest : 요청 대상
         *  sec-fetch-mode : 요청 모드
         *  sec-fetch-site : 출처(origin)와 요청된 resource 사이의 관계
         *  sec-fetch-user : 사용자가 시작한 요청일 때만 보내짐 (값은 항상 ?1을 가짐)
         *  cache-control : 캐시 설정
         *  upgrade-insecure-requests
         *  user-agent : 현재 사용자가 어떤 클라이언트(OS, browser 포함)을 이용해 보낸 요청인지 명시
         * */
        Enumeration<String> headerNames = req.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            System.out.println(headerNames.nextElement());
        }

         // 클라이언트가 서버로부터 어떤 타입의 응답을 수락할 수 있는지 명시
        System.out.println("accept : " + req.getHeader("accept"));
        // 클라이언트가 서버로부터 어떤 인코딩 방식의 응답을 수락할 수 있는지 명시 (예: gzip, deflate)
        System.out.println("accept-encoding : " + req.getHeader("accept-encoding"));
        // 클라이언트가 선호하는 언어를 명시
        System.out.println("accept-language : " + req.getHeader("accept-language"));
        // 연결 상태를 유지할 것인지 여부를 나타내는 헤더 (기본적으로 keep-alive로 설정됨)
        System.out.println("connection : " + req.getHeader("connection"));
        // 요청이 보내진 서버의 도메인과 포트 번호를 명시
        System.out.println("host : " + req.getHeader("host"));
        // 클라이언트가 현재 페이지를 요청하기 전에 방문한 페이지의 URL을 명시
        System.out.println("referer : " + req.getHeader("referer"));
        // 요청이 어떤 목적으로 사용될 것인지 명시 (예: document, image)
        System.out.println("sec-fetch-dest : " + req.getHeader("sec-fetch-dest"));
        // 요청의 모드 (예: navigate, cors)
        System.out.println("sec-fetch-mode : " + req.getHeader("sec-fetch-mode"));
        // 요청이 같은 사이트 내에서 이루어진 것인지 여부를 나타냄
        System.out.println("sec-fetch-site : " + req.getHeader("sec-fetch-site"));
        // 사용자가 직접 시작한 요청인지 여부를 나타냄 (항상 ?1 값을 가짐)
        System.out.println("sec-fetch-user : " + req.getHeader("sec-fetch-user"));
        // 클라이언트가 캐시 관련 설정을 서버에 전달
        System.out.println("cache-control : " + req.getHeader("cache-control"));
        // 클라이언트가 보안되지 않은 요청을 업그레이드할 것을 서버에 요청
        System.out.println("upgrade-insecure-requests : " + req.getHeader("upgrade-insecure-requests"));
        // 클라이언트의 사용자 에이전트를 나타내며, 사용 중인 브라우저 및 운영체제 정보가 포함됨
        System.out.println("user-agent : " + req.getHeader("user-agent"));
    }
}
