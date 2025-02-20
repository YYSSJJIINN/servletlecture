package com.ohgiraffers.filter.section02.uses;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.IOException;

@WebServlet("/member/register")
public class RegisterMemberServlet extends HttpServlet {

    /* 설명. 간단한 회원 정보를 입력 받아 회원 가입을 위한 서블릿 요청을 해보는데, 이 때 크게 두 가지 문제점이 있다.
     *  1. POST 전송 시 한글 깨짐 현상 -> 인코딩 설정 Note. (Tomcat v9 이하만 해당)
     *  2. 사용자 비밀번호 노출 -> 비밀번호 암호화 처리
     *  물론 서블릿 쪽에서 작성을 해도 되지만, 서블릿에 요청하기 전에 미리 request에 대한 인코딩 처리나
     *  매번 member 서비스에 사용할 암호화 처리와 같은 공통적인 내용을 서블릿 요청 전에 처리할 수 있다면 유지보수하기 편리할 것이다.
     *  인코딩 필터와 암호화 필터를 이용해 현재 서블릿에 도착하기 전까지 이 두 가지를 해결해 볼 것이다.
     * */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        System.out.println("[RegisterMemberServlet](doPost) 호출됨...");

        System.out.println("---------- userId 추출 ----------");
        String userId = req.getParameter("userId");
        System.out.println("userId = " + userId);

        System.out.println("---------- password 추출 ----------");
        String password = req.getParameter("password");
        System.out.println("password = " + password);

        System.out.println("---------- name 추출 ----------");
        String name = req.getParameter("name");
        System.out.println("name = " + name);

        /* 비밀번호를 DB에서 쿼리해왔다 가정하고 전달된 비밀번호를 비교해보자*/
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        /* 데이터베이스에는 비밀번호 평문이 아니라 암호문이 저장되어야 한다.
        *  그러나 한번 암호화된 암호문은 평문으로 복호화할 수 없다.
        *  따라서 사용자가 입력한 평문을 암호문으로 변경한 후, 암호문과 암호문끼리 비교해야 한다.
        *  이 때, matches() 메서드를 이용하면 해당 과정을 편하게 구현해
        *  ture/false 갑승로 일치/불일치 판정을 받을 수 있다.
        * */
        // matches가 사용되었으므로, 반환형은 T or F이다.
        System.out.println("비밀번호 pass01이 입력되었나? : " + passwordEncoder.matches("pass01", password));
        System.out.println("비밀번호 pass02이 입력되었나? : " + passwordEncoder.matches("pass02", password));
    }
}
