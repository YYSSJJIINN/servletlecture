package com.ohgiraffers.params.section01.querystring;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Date;

@WebServlet("/querystring")
public class QueryStringTestServlet extends HttpServlet {

    /* 설명.
     *  HttpServlet 클래스의 service method는 요청 방식에 따라 GET 요청에 대해서
     *  doGet() method를 호출하면서 request와 response를 전달한다.
     *  -> 톰캣 서블릿 컨테이너가 요청 url로 매핑된 Servlet클래스의 인스턴스를 생성하여 service method를 호출하고
     *     HttpServlet을 상속받아 오버라이딩한 현재 클래스의 doGet() method가 동적바인딩에 의해 호출된다.
     *  ==========================================================================================================
     *  service로부터 전달받은 HttpServletRequest는 요청 시 전달한 값을 getParameter() method로 추출해 올 수 있다.
     *  이때 인자로 input 태그에 지정한 name속성의 값을 문자열 형태로 전달해주면 된다.
     *  화면에서 전달한 form 내의 모든 input 태그의 값들을 HashMap으로 관리하고 있으므로,
     *  원하는 값을 찾기 위해서는 key역할을 하는 문자열이 필요하기 때문이다.
     * */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        System.out.println("GET 방식 요청 도착함~");

        String name = req.getParameter("name");
        System.out.println("name = " + name);

        /* HttpServletRequest의 getParameter() 메서드는 반환형이 String으로 고정되어 있다.
        *  즉, 화면단에서 전달된 데이터들이 텍스트 정보이건, 숫자 정보이건, 날짜 정보이건 상관 없이
        *  Java Servlet 영역으로 넘어오면 모두 String으로 도착하게 된다.
        *  따라서 Java 진영에서 취급하기 쉬운 적절한 타입으로 형변환 해줘야 한다.*/
        int age = Integer.parseInt(req.getParameter("age"));    // String 을 int로 파싱.
        System.out.println("age = " + age);

        /* java.sql.Date 타입으로 저장하고 싶은 경우에도 전달된 파라미터를 String에서 java.sql.Date로 변환해야한다.
        *  이 때, java.sql.Date의 valueOf() 메서드를 사용하면 쉽게 형변환 할 수 있다.
        *  ---------------------------------------------------------------------------
        *  개인적인 추천으로는 날짜 및 시간 정보는 팀끼리 포맷을 지정해둔 후 String으로 취급하는게 훨씬 편함...
        *  예) 날짜 : yyyy-MM-dd (년월일)
        *  예) 시간 : HH:mm:ss (24시간제)
        * */
        Date birthday = Date.valueOf(req.getParameter("birthday"));
        System.out.println("birthday = " + birthday);

        /* 라디오 버튼으로 전달된 값은 여러개의 값 중 하나만 선택되어 전달되기 때문에
        *  그대로 String 갑스올 전달되어 형변환을 할 피룡는 없다.(가공이 필요한 경우에만 가공해도 됨)
        * */
        String gender = req.getParameter("gender");
        System.out.println("gender = " + gender);

        /* 셀렉트 박스도 라디오 버튼과 동일하게 어차피 하나의 값만 선택되어 전달된다. */
        String nationality = req.getParameter("national");
        System.out.println("nationality = " + nationality);

        /* 체크 박스의 경우, 다수의 값을 입력받을 수 있는 인터페이스이기 때문에 선택된 값들은 문자열 배열로 전달된다.
        *  이 때, getParameter() 대신, getParameterValues() 메서드를 사용해야 한다.
        * */
        String[] hobbies = req.getParameterValues("hobbies");
        for (String h : hobbies) {
            System.out.println("h = " + h);
        }
    }
}
