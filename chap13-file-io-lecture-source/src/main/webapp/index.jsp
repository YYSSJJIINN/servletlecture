<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>JSP - Hello World</title>
</head>
<body>
    <h1>Chapter 13. File Upload</h1>

    <h3>fileUpload</h3>

    <h4>1. commons files upload(CRUD 중 Create)</h4>
    <p>commons fileupload를 이용하면 multifile도 가능하며, 파일을 여러 input태그로 업로드 하는 것도 가능하다.</p>
    <form action="commons/multi" method="post" encType="multipart/form-data">
        <input type="file" name="singlefile" multiple><br>
        <input type="file" name="singlefile2" multiple><br>     <%-- multiple은 단일선택도 다중선택도 모두 가능하다는 뜻이다. --%>
        <input type="text" name="description">
        <button type="submit">전송</button>
    </form>

    <hr>

    <h4>2. transfer to thumbnail image</h4>
    <button onclick="location.href='transferToThumbnail'">썸네일이미지 변환하기</button>
</body>
</html>