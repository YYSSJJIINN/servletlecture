package com.ohgiraffers.forward.section01.forward;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Enumeration;

@WebServlet("/forward")
public class ReceiveInformationServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        System.out.println("POST / forward 요청 접수~");

        /* 서블릿의 임무:
        *  1. 요청 정보 받기
        *  2. 비즈니스 로직 처리
        *  3. 응답 내보내기
        * */

        /* 1. 요청 정보 받기 */
        // 클라이언트(브라우저)로부터 전달된 request 객체에서 요청 정보 추출.
        String userId = req.getParameter("userId");
        String password = req.getParameter("password");

        System.out.println("userId = " + userId);
        System.out.println("password = " + password);

        /* 2. 비즈니스 로직 처리 */
        /* 원래 이 파트에서 userId와 password를 가지고 해당 정보와 일치하는 회원이 DB에 존재하는지
        *  조회하는 비즈니스 로직이 수행되어야 한다. (향후 MVC에서 배울 내용)
        *  현재는 포워딩이라고 하는 기술을 배우는 중이니 DB에서 회원 정보가 제대로 조회되었다는 가정하에
        * "xxx님 환영합니다"와 같은 로그인 성공 시 볼 법한 메세지를 출력하는 화면을 만들어 응답할 것이다.
        * */

        /* 3. 응답 내보내기 -> 해당 서블릿이 아닌, 응답 페이지를 잘 그리는 다른 서블릿에게 책임을 위임*/
        /* 3-1. 비즈니스 로직을 수행했더니, 사용자의 이름이 홍길동이고 나이가 16살이라는 결과를 받았고,
        *  이 사실을 다음 서블릿에게도 전달해주고자 공유 데이터를 저장할 수 있는 request 객체 내 attribute를 사용.
        * */
        req.setAttribute("userName", "홍길동");
        req.setAttribute("userAge", 16);

        /* 필기. setAttribute()와 getAttribute()
         *  setAttribute():
         *   - 요청 범위(request 객체)에 데이터를 속성(attribute)으로 추가.
         *   - 사용자 인증 정보, form 입력값 처리 이후의 결과, 요청 처리 시간 등을 저장할 때 사용.
         *  getAttribute():
         *   - 요청 범위(request 객체)에 데이터를 속성(attribute)으로 검색.
         *   - 검색 결과는 Object 타입으로 반환함.
         *   - 요청 처리에 사용하기 위해 저장된 속성 값을 검색, 다음 페이지나 뷰로 전달할 때 사용.
         * */

        /* 3-2. 처리 결과를 그릴 다음 서블릿에게 포워딩 시키기.
         * 어떤 서블릿으로 위임(포워딩) 할 것인지 대상 서블릿을 지정하는 인스턴스(RequestDispatcher)를 생성하고,
         * forward() 메서드를 통해 요청과 응답에 대한 정보를 전달하여 나머지 작업을 수행하도록 위임한다.
         *  ===========================================================================================================
         *  RequestDispatcher:
         *  - 서블릿 간에 요청을 전달(포워딩)하거나, 요청에 대한 응답을 HTML 페이지나 다른 서블릿 또는 JSP로
         *    포함(include)할 때 사용되는 객체.
         *  - 즉, 서블릿, HTML 파일, JSP 등 다른 웹 리소스에 요청을 위임하는 주체다.
         *  - 참고로 Dispatcher는 배차 담당자라는 뜻.
         *  ===========================================================================================================
         *  'print'라는 이름을 가진 서블릿으로 요청을 전달할 인스턴스 생성.
         *  이 때 생성된 인스턴스는 request 및 response 객체를 가지고 본인이 가야 할 다른 서블릿으로 전달된다.
        * */
        RequestDispatcher rd = req.getRequestDispatcher("print");   // 포워딩 받을 서블릿의 URL 매핑 입력
        rd.forward(req,resp);
    }
}
