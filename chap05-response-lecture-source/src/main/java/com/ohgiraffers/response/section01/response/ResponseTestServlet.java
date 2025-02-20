package com.ohgiraffers.response.section01.response;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

@WebServlet("/response")
public class ResponseTestServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        System.out.println("GET /response 호출됨~");

        /* 서블릿의 주요 역할 3가지:
        *  1. 요청 받기 : HTTP method GET/POST 요청에 따라 parameter로 전달된 사용자 데이터를 꺼내올 수 있다.
        *  2. 비즈니스 로직 처리 : DB 접속 및 CRUD에 대한 로직 처리 시작 부분 -> MVC2 구조 기반에서는 서비스 계층을 호출해서 해결함.
        *  3. 응답하기 : 문자열로 동적인 웹 페이지(HTML 요소)를 생성하고, 스트림을 생성해 내보낸다(SSR 방식)
        * ---------------------------------
        *  해당 챕터에서는 3번째 역할, 즉 Stream을 통해 사용자 브라우저로 페이지를 제작해 내보내는 역할을 수행해 볼 것이다.
        * */

        /* 1. 문자열(여기서는 StringBuilder를 사용)을 이용해 사용자(브라우저)에게 내보낼 응답 페이지를 작성한다.
        *  이처럼 서버측에서 클라이언트에게 보여줄 페이지를 작성하는 방식을 SSR(Server-side Rendering)이라고 부른다.
        * */
        StringBuilder respBuilder = new StringBuilder();
        respBuilder.append("<!DOCTYPE html>\n")
                    .append("</html>\n")
                    .append("<head>\n")
                    .append("</head>\n")
                    .append("<body>\n")
                    .append("<h1>Hello World! 안녕 세상아!</h1>\n")
                    .append("<p>현재시간 : " + new Date() + "</p>")
                    .append("</body>\n")
                    .append("</html>\n");

        // ----------------------------------------- Tomcat v9 이하
        /* 필기.
         *  1. MIME 타입 설정: 클라이언트 브라우저로 내보낼 데이터의 타입을 응답 헤더에 설정하는데,
         *     content-type 헤더의 값을 지정해주지 않으면 null이 기본 값이므로 content-type 값을 정의해야 한다.
         * */
//      System.out.println("default response type : " + resp.getContentType());

        /* 필기. contentType을 text/plain으로 설정하면 브라우저 측에서는 HTML 태그를 태그가 아닌 문자열로 인식한다. */
//      resp.setContentType("text/plain");
        /* 필기. 브라우저 측에서는 content-type이 text/html로 설정되어야 정상적으로 HTML 태그를 인식하고 페이지를 그릴 수 있다. */
//      resp.setContentType("text/html");

        /* 필기. 2. 인코딩 설정: 응답 시에도 별도 인코딩을 지정해주지 않으면 기본 설정값(UTF-8)을 따른다. */
//      System.out.println("default response encoding : " + resp.getCharacterEncoding());

        /* 필기. 인코딩 방식을 명시적으로 변경할 수 있으며, contentType과 인코딩 설정을 동시에 진행할 수도 있다.
         *  인코딩을 설정할 때 주의할 점은 반드시 getWriter()로 Stream을 얻어오기 전에 설정해야 한다.
         * */
        /* Note. 참고로 Tomcat v10 이상은 인코딩 설정 전에 stream을 얻어 와도 에러가 발생하지 않음. */
//      resp.setCharacterEncoding("UTF-8");               // 인코딩 설정
//      resp.setContentType("text/html; charset=UTF-8");   // content-type 설정 + 인코딩 설정
//      System.out.println("changed response encoding : " + resp.getCharacterEncoding());   // 변경된 인코딩 확인
        // --------------------------------------------------------

        /* 2. MIME 타입 설정 : Tomcat v10 이상부터는 MIME 타입만 명시해도 인코딩이 UTF-8로 자동 설정됨 */
        System.out.println("Response MIME 타입 지정 전 : " + resp.getContentType());     // 처음엔 null 출력됨
        resp.setContentType("text/html");       // "text/plain"으로 작성하면 html로 인식하지 않아서 작성한 트리가 다 보인다.
        System.out.println("Response MIME 타입 지정 후 : " + resp.getContentType());     // 이제 text/html로 설정됨

        /* 3-1. 클라이언트 브라우저로 응답하기 위해서 HttpServletResponse의 getWriter() 메서드로 PrintWriter 인스턴스를 반환받는다.
        *  PrintWriter는 BufferedWriter와 형제격인 클래스지만, 더 많은 형태의 생성자를 제공하고 있는 범용성으로 인해
        *  상태적으로 더 많이 사용되는 편이다.
        *  */
        PrintWriter out = resp.getWriter();     // 입출력용 스트림 생성

        /* 3-2. 준비한 스트림을 통해 응답 페이지 문자열을 클라이언트 측으로 내보냄 (Tomcat 버전 주의!) */
//        out.print(respBuilder.toString());      // Tomcat v9 이하
        out.print(respBuilder);                 // Tomcat v10 이상

        /* 3-3. 스트림이 사용한 리소스 반납 */
        out.flush();        // 버퍼에 잔류한 데이터를 강제로 비움
        out.close();        // 스트림 반환(Garbage Collector가 메모리를 수거해감)
    }
}
