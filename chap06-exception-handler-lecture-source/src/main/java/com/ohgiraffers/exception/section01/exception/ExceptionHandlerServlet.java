package com.ohgiraffers.exception.section01.exception;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletMapping;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

@WebServlet("/showMyErrorPage")
public class ExceptionHandlerServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        System.out.println("Exception Handler 서블릿 호출됨...");

//        System.out.println("-----------------Request 객체 속 Attributes 확인-----------------");
//        Enumeration<String> attrName = req.getAttributeNames();
//        while(attrName.hasMoreElements()) {
//            System.out.println(attrName.nextElement());
//        }
        /* 아래 값들이 출력됨:
        jakarta.servlet.forward.request_uri
        jakarta.servlet.forward.context_path
        jakarta.servlet.forward.servlet_path
        jakarta.servlet.forward.mapping
        jakarta.servlet.error.message
        jakarta.servlet.error.servlet_name
        jakarta.servlet.error.request_uri
        jakarta.servlet.error.status_code
        * */

        /* 설명. request의 attribute에 에러와 관련된 것들을 활용해 에러 전용 페이지를 동적으로 만들어 응답해본다. */
//        String forwardRequestURI = (String) req.getAttribute("jakarta.servlet.forward.request_uri");
//        String contextPath = (String) req.getAttribute("jakarta.servlet.forward.context_path");
//        String servletPath = (String) req.getAttribute("jakarta.servlet.forward.servlet_path");
//        HttpServletMapping mapping = req.getHttpServletMapping();
//        String errorRequestURI = (String) req.getAttribute("jakarta.servlet.error.request_uri");
        Integer statusCode = (Integer) req.getAttribute("jakarta.servlet.error.status_code");
        String message = (String) req.getAttribute("jakarta.servlet.error.message");
        String servletName = (String) req.getAttribute("jakarta.servlet.error.servlet_name");

        System.out.println("-------------- Parsed Attributes in Request --------------");
        /* 원래 요청의 URI */
//        System.out.println("forwardRequestURI = " + forwardRequestURI);
//        /* 원래 요청의 컨텍스트 경로 */
//        System.out.println("contextPath = " + contextPath);
//        /* 원래 요청의 서블릿 경로 */
//        System.out.println("servletPath = " + servletPath);
//        /* 요청 매핑 정보 */
//        System.out.println("mapping = " + mapping);
//        /* 매핑된 서블릿 이름 */
//        System.out.println("mapping.getServletName() = " + mapping.getServletName());
//        /* 매칭된 값 (서블릿 매핑에서 추출한) */
//        System.out.println("mapping.getMatchValue() = " + mapping.getMatchValue());
//        /* 매핑 패턴 */
//        System.out.println("mapping.getPattern() = " + mapping.getPattern());
//        /* 매핑된 매치 유형 */
//        System.out.println("mapping.getMappingMatch() = " + mapping.getMappingMatch());
//        /* 에러가 발생한 요청의 URI */
//        System.out.println("errorRequestURI = " + errorRequestURI);
        /* 에러 상태 코드 */
        System.out.println("statusCode = " + statusCode);
        /* 에러 메시지 */
        System.out.println("message = " + message);
        /* 에러가 발생한 서블릿 이름 */
        System.out.println("servletName = " + servletName);

        StringBuilder errorPage = new StringBuilder();
        errorPage.append("<!doctype html>\n")
                .append("<html>\n")
                .append("<head>\n")
                .append("</head>\n")
                .append("<body>\n")
                .append("<h1>")
                .append(statusCode)
                .append(" - ")
                .append(message)
                .append("<br>\n")
                .append("<p>에러 발생한 서블릿 이름: ")
                .append(servletName)
                .append("</p>")
                .append("</h1>\n")
                .append("</body>\n")
                .append("</html>");

        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();

        out.print(errorPage);
        out.flush();
        out.close();
    }
}
