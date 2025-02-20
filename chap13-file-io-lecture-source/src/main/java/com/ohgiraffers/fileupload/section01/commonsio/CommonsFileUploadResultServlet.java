package com.ohgiraffers.fileupload.section01.commonsio;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

@WebServlet("/commons/result")
public class CommonsFileUploadResultServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        System.out.println("[CommonsFileUploadResultServlet] doGet 메서드 호출됨...");

        /* 목차. 1. 요청 받고 필요한 값 뽑아내기 */
        // 업로드한 파일 정보가 들어있는 세션 객체 준비
        HttpSession session = req.getSession();

        // 앞서 브라우저에서 전달받은 폼 데이터가 들어있는 List 및 Map을 세션에서 뽑아내기
        List<Map<String, String>> fileList = (List<Map<String, String>>) session.getAttribute("fileList");
        Map<String, String> parameter = (Map<String, String>) session.getAttribute("parameter");

        System.out.println("폼 데이터(파일) = " + fileList);
        System.out.println("폼 데이터(기타) = " + parameter);
        /* 참고:
         *  사실 현재 세션에 담겨있는 폼 데이터는 해당 서블릿이 응답을 그려낸 이후부터는 필요 없는 데이터라고 볼 수 있다.
         *  왜냐하면 이전 서블릿에 대해 다른 요청이 들어오면 그 때는 다른 폼 데이터가 전송될 것이기 때문이다.
         *  따라서 해당 코드의 가장 뒷 부분에서 화면을 출력한 이후 세션에서 이 일회성 데이터를 지워낼 것이다.
         *  그래야 서버 측 리소스를 더 효율적으로 관리할 수 있을 것이다.
         * */

        /* 목차. 2. 비즈니스 로직 수행 : 해당 서블릿은 화면을 그려내는 것이 비즈니스 로직임. */
        // PrintWriter 객체를 생성해 응답을 찍어낼 준비
        PrintWriter out = resp.getWriter();

        // 설정: 응답 콘텐츠 타입을 HTML로 설정하고 인코딩을 UTF-8로 지정
        resp.setContentType("text/html");

        // HTML 문서 출력 시작
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<title>File Upload Result</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>파일 업로드 결과 페이지 임다~</h1>");

        // 핵심 로직 #1 : 업로드된 파일이 있는 경우 : 파일 목록을 HTML 문서로 출력
        if (fileList != null && !fileList.isEmpty()) {
            out.println("<h2>업로드된 파일 목록:</h2>");
            out.println("<ul>");    // <ul> 시작 태그

            // (동적으로)파일 개수만큼 <li> 태그 생성
            for (Map<String, String> file : fileList) {
                String originFileName = file.get("originFileName"); // 원본 파일명
                String savedFileName = file.get("savedFileName");   // 저장된 파일명(랜덤화)
                String savePath = file.get("savePath");             // 저장 경로

                out.println("<li>");
                out.println("원본 파일명: " + originFileName + "<br>");
                out.println("저장된 파일명: " + savedFileName + "<br>");
                out.println("저장 경로: " + savePath + "<br>");
                out.println("</li>");
            }

            out.println("</ul>");   // <ul> 종료 태그
        } else {
            // 업로드된 파일이 없는 경우 : 업로드된 파일이 없다고 출력
            out.println("<p>업로드된 파일이 없습니다.</p>");
        }

        // 핵심 로직 #2 : 업로드와 함께 전송된 기타 파라미터를 출력
        if (parameter != null && !parameter.isEmpty()) {
            out.println("<h2>전송된 기타 파라미터:</h2>");
            out.println("<ul>");

            for (Map.Entry<String, String> entry : parameter.entrySet()) {
                out.println("<li>" + entry.getKey() + " : " + entry.getValue() + "</li>");
            }

            out.println("</ul>");
        } else {
            out.println("<p>전송된 기타 파라미터가 없습니다.</p>");
        }

        out.println("</body>");
        out.println("</html>");

        /* 목차. 3. 응답 전송 */
        out.flush();
        out.close();

        // 가져온 후, 세션에서 제거 (데이터를 일회성으로 사용하기 위해)
        session.removeAttribute("fileList");
        session.removeAttribute("parameter");

        System.out.println("응답 생성 후 세션 정보:");
        Enumeration<String> sessionNames = req.getSession().getAttributeNames();
        while(sessionNames.hasMoreElements()) {
            System.out.println(sessionNames.nextElement());
        }
    }
}
