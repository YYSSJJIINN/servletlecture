<%@ page
        contentType="text/html;charset=UTF-8"
        language="java"
        import="java.util.List"
        import="java.util.Map"
%>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>파일 업로드 결과 페이지</h1>
<%
    // 업로드한 파일 정보가 들어있는 세션 객체 준비할 필요 없이 JSP 내장 객체인 session 사용
    // 앞서 브라우저에서 전달받은 폼 데이터가 들어있는 List 및 Map을 세션에서 뽑아내기
    List<Map<String, String>> fileList = (List<Map<String, String>>) session.getAttribute("fileList");
    Map<String, String> parameter = (Map<String, String>) session.getAttribute("parameter");

    // 가져온 후, 세션에서 제거 (데이터를 일회성으로 사용하기 위해)
    session.removeAttribute("fileList");
    session.removeAttribute("parameter");
%>

<%
    // 업로드된 파일이 있는 경우, 파일 목록을 출력
    if (fileList != null && !fileList.isEmpty()) {
%>
<h2>업로드된 파일 목록:</h2>
<ul>
    <%
        for (Map<String, String> file : fileList) {
            String originFileName = file.get("originFileName");
            String savedFileName = file.get("savedFileName");
            String savePath = file.get("savePath");
    %>
    <li>
        원본 파일명: <%= originFileName %><br>
        저장된 파일명: <%= savedFileName %><br>
        저장 경로: <%= savePath %><br>
    </li>
    <%
        }
    %>
</ul>
<%
} else {
%>
<p>업로드된 파일이 없습니다.</p>
<%
    }
%>

<%
    // 업로드와 함께 전송된 기타 파라미터를 출력
    if (parameter != null && !parameter.isEmpty()) {
%>
<h2>전송된 기타 파라미터:</h2>
<ul>
    <%
        for (Map.Entry<String, String> entry : parameter.entrySet()) {
    %>
    <li><%= entry.getKey() %> : <%= entry.getValue() %></li>
    <%
        }
    %>
</ul>
<%
} else {
%>
<p>전송된 기타 파라미터가 없습니다.</p>
<%
    }
%>
</body>
</html>