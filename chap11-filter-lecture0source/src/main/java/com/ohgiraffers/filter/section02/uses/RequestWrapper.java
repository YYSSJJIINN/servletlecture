package com.ohgiraffers.filter.section02.uses;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/* 현재 주된 목적은 클라이언트가 전송하는 form 데이터들 중, 'password'라는 키값으로 넘어오는 특정 데이터를 감지하여
*  이 데이터를 평문이 아닌 암호문으로 변환하는 것이다.
*  그러려면 우리가 지금까지 Servlet을 배우면서 사용한 HttpServlet 객체의 getParameter() 메서드를
*  위와 같이 동작하게끔 재정희 해야한다.
*  그러나, HttpServletRequest 객체 자체를 사용자가 조작하지 못하도록 막아 놓았다.
*  이를 우회하고자 원본 HttpServletRequest 객체 자체를 조작하는 것이 아닌, 감싼 Wrapper 객체를 이용하여
*  getParameter()를 우리가 원하는 방식으로 동작하게끔 재정의할 것이다.
* */
public class RequestWrapper extends HttpServletRequestWrapper {

    /* 부모인 HttpServletRequestWrapper 클래스에 기본생성자가 정의되어 있지 않다.
    *  따라서 원본 request 객체를 전달해줄 매개체인 생성자가 필요하다.
    * */
    public RequestWrapper(HttpServletRequest request) {
        super(request);
    }

    @Override
    public String getParameter(String name) {

        System.out.println("[RequestWrapper](getParameter) 호출됨...");

        String value = "";

        // String userId = req.getParameter("userId");
        // String password = req.getParameter("password");
        // String name = req.getParameter("name");
        // 위의 것 중에서 password가 아닌 Id나 name이 if문에 들어오면 탈락하므로 else로 가고 값이 value에 들어가 그대로 반환된다.
        if("password".equals(name)) {
            System.out.println("현재 /member/*로 시작하는 어떤 서블릿이 password 파라미터에 접근하는 것을 감지");

            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

            value = passwordEncoder.encode(super.getParameter(name));   // super.getParameter(name)는 원래 부모의 파라미터를 호출한다.

            System.out.println("암호화된 비밀번호 : " + value);
        } else {
            System.out.println("현재 /member/*로 시작하는 어떤 서블릿이 password 파라미터에 접근하는 것을 감지");
            value = super.getParameter(name);
        }

        return value;
    }
}
