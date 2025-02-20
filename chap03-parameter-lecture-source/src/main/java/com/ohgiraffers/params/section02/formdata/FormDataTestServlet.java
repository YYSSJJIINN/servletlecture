package com.ohgiraffers.params.section02.formdata;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

@WebServlet("/formdata")
public class FormDataTestServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        System.out.println("POST 방식 요청 도착함~");

        // ----------------------------- Tomcat 8 or 9
        /* Tomcat v9 이하의 환경일 경우 아래 내용 숙지 필요:
         *  POST 요청으로 전달받은 데이터에 한글이 포함되어 있으면 해당 문자는 인코딩이 맞지 않아 깨진다.
         *  ------------------------------------------------------------------------------------------------------------
         *  GET 방식의 데이터는 HTML charset에 기술된 인코딩 방식으로 브라우저가 한글을 이해하고, URLEncoder를 이용해
         *  %문자로 변환시켜 URL 요청으로 전송한다.
         *  이 때, 헤더(header)의 내용은 ascii 코드로 전송되기 때문에 어떤 언어이든 서버에 설정된 인코딩 방식과
         *  일치한다면 해석하는데 문제가 없으므로 한글이 깨지지 않는다.
         *  GET 요청은 보통 서버의 리소스를 가져오는 행위를 요청하는 http 요청 방식이기에 별도의 데이터가 필요 없어
         *  요청 본문, 즉 페이로드(payload)는 해석하지 않는다.
         *  ------------------------------------------------------------------------------------------------------------
         *  반면, POST 요청은 서버의 기존 리소스에 데이터를 추가하는 요청이기 때문에 요청하는 데이터가 필요한 경우가
         *  대부분이다. (데이터를 추가하는 목적 외, 다양하게 사용됨.)
         *  서버의 기존 리소스에 추가해야 하는 정보를 페이로드에 key&value 방식으로 담아 전송하는데, 헤더와는 별개로
         *  URLEncoder를 이용하지 않고 페이지 meta에 기술된 charset에 따라 UTF-8로 해석된 데이터를 서버로 전송한다.
         *  기본적으로 서버단에서 페이로드를 디코딩 하는 방식이 별도로 명시되어 있지 않다.
         *  그래서 request.getCharacterEncoding()을 호출해보면 null을 반환하게 되는데, 인코딩된 방식을 명시하지 않으면
         *  기본 ISO-8859-1로 해석하므로 값을 꺼내오면 한글인 글자가 깨지는 현상이 발생한다.
         *  따라서 요청 속에 담긴 parameter를 꺼내오기 전에 parameter값을 해석할 인코딩 방식이 UTF-8임을
         *  setCharacterEncoding(String encType)으로 지정주어야 브라우저에서 요청한 인코딩 방식과 일치하여
         *  한글 깨짐 현상을 막을 수 있다.
         * */
        // 현재 설정된 인코딩 방식 조회
//        System.out.println(req.getCharacterEncoding());
        // 인코딩 방식을 UTF-8로 설정
//        req.setCharacterEncoding("UTF-8");
        // 변경된 인코딩 확인
//        System.out.println(req.getCharacterEncoding());
        // -----------------------------

        /* GET 방식과 동일하게 HttpServletRequest의 getParameter() 메서드를 사용하면 된다. (인코딩 확인 필수) */
        String name = req.getParameter("name");
        System.out.println("name = " + name);

        /* 만약, 클라이언트 단에서 요청한 데이터의 key 값 및 value 값을 인지하지 못한 상태라면
        *  getParameter() 메서드를 사용할 수 없을 것이다.
        *  이 때, 모든 데이터의 key 값을 이용해 전송된 파라미터를 일괄 처리할 수 있다.
        *  */

        Map<String, String[]> reqMap = req.getParameterMap();

        // 프론트에서 전달된 파라미터들의 key값만 추출해서 Set으로 변환
        Set<String> keySet = reqMap.keySet();

        // 추출된 key값들을 확인하기 위해서 반복자로 확인
        Iterator<String> keyIter = keySet.iterator();

        System.out.println("전달된 파라미터들 분해해보기(K-V)");
        while(keyIter.hasNext()) {
            String key = keyIter.next();
            String[] values = reqMap.get(key);

            System.out.println("key = " + key);
            for(int i = 0; i < values.length; i++) {
                System.out.println("values[" + i + "] : " + values[i]);
            }
        }

        /* GET 요청 방식은 URL을 통해 요청 내 파라미터를 쉽게 확인할 수 있는 반면,
        *  POST 요청은 매번 프론트의 코드(여기서는 JSP)를 보면서 확인할 수 없으니
        *  getParameterMap 또는 getParameterNames 등의 메서드를 사용해서
        *  요청 내 파라미터를 확인해볼 수 있다.
        * */


        String age = req.getParameter("age");
        System.out.println("age = " + age);

        String birthday = req.getParameter("birthday");
        System.out.println("birthday = " + birthday);
    }
}
