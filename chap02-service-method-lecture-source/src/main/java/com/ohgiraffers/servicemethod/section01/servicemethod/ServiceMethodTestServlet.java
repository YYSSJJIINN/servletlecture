package com.ohgiraffers.servicemethod.section01.servicemethod;



import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/* @WebServlet 어노테이션에 URL 매핑을 의미하는 value 값을 정의할 수 있다.
* 이 때, 어노테이션ㄴ에 정의할 속성이 value 설정값 뿐이라면,
* 단순 문자열로 URL 매핑을 정의할 수도 있다.
* */
//@WebServlet(value = "/request-service")
@WebServlet("/request-service")
public class ServiceMethodTestServlet extends HttpServlet {

    // HttpServletRequest 보다 ServletRequest가 더 넓은 범위인 상위존재다.
    // doGet과 doPost를 품고 있는 상위존재인 service는 통신규약인 HTTP외의 것이 개발되면 수용해야하기 때문에
    // Http가 없는 형태를 사용한다.
    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        System.out.println("service() 메서드 호출됨!");

        /* 설명. 나중에 HTTP를 대체할 프로토콜이 존재한다면 HttpServletRequest는 다른 클래스로 대체되어야 한다.
         *  따라서 ServletRequest라는 추상화된 타입으로 사용하고, 실제 인스턴스는 HttpServletRequest로 사용하여
         *  나중에 service method의 인자 타입은 변경하지 않고 다른 프로토콜을 사용하는 Request로 변경이 가능하다.(다형성)
         *  =================================================================================================
         *  하지만 현재 사용되는 프로토콜은 HTTP 프로토콜로, HttpServletRequest 타입이다.
         *  HttpServletRequest는 ServletRequest 타입을 상속받아서 구현하였으며, HTTP 프로토콜의 정보를 담고 있기 때문에
         *  실제 사용 시에는 HttpServletRequest 타입으로 다운캐스팅 해서 사용해야 한다.
         *  ServletResponse도 같은 맥락이다.
         *  =================================================================================================
         *  HttpServlet 클래스의 service(ServletRequest request, ServletResponse) method에서는
         *  인자로 전달받은 request와 response를 HttpServletRequest와 HttpServletResponse로 다운캐스팅 한다.
         * */
        // 강제 형변환
        // ServletRequest 및 ServletResponse 타입인 매개변수를 HttpServlet으로 다운캐스팅.
        HttpServletRequest httpReq = (HttpServletRequest) req;
        HttpServletResponse httpRes = (HttpServletResponse) res;

        // 다운캐스팅한 HttpServletRequest의 getMethod()를 호출하면 현재 처리중인 요청의 HTTP Method를 확인 가능.
        String httpMethod = httpReq.getMethod();
        System.out.println("현재 요청의 httpMethod = " + httpMethod);    // GET 또는 POST

        /* GET/POST 요청을 구분해서 doGet() 또는 doPost() 메서드를 호출해서 흐름을 이어갈 수 있는데,
        * 이 때 전달인자로 service()가 받았던 전달인자(HTTP로 다운캐스팅 된)를 그대로 전달해주면 된다.
        * */
        if("GET".equals(httpMethod)) {
            System.out.println("현재 요청은 GET 요청이니, doGet()을 호출해보겠습니다.");
            doGet(httpReq, httpRes);
        } else if("POST".equals(httpMethod)) {
            System.out.println("현재 요청은 POST 요청이니, doPost()을 호출해보겠습니다.");
            doPost(httpReq, httpRes);
        }
    }

    /* 실제로 HTTP 스펙에 정의된 Method는 GET, POST 외에도 여러가지(9개)가 있다.
    *  https://developer.mozilla.org/ko/docs/Web/HTTP/Methods
    * 하지만 주로 사용되는 method는 GET과 POST이다.
    *  따라서 여기서는 GET과 POST에 대한 처리만 간략하게 구경해보자.
    * */

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("doGet() 메서드 호출됨!");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("doPost() 메서드 호출됨!");
    }
}
