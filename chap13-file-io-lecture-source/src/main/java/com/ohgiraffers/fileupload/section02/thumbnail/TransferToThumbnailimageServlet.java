package com.ohgiraffers.fileupload.section02.thumbnail;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.coobird.thumbnailator.Thumbnails;

import java.io.File;
import java.io.IOException;

/* 썸네일 게시판 형태일 경우 원본 이미지 파일을 로드해서 사용자에게 보여주면
*  페이지 렌더링 시간도 느리고 모바일 환경일 경우 많은 야으이 데이터를 소모할 수도 있다.
*  따라서 썸네일 게시판 이미지용으로 원본 이미지를 원본과 썸네일용으로 2개를 별도 저장하여
*  썸네일 이미지만 보여주는 작업이 필요하다.
*  여기서는 썸네일 이미지를 변환하는 thumbnailator 라이브러리를 사용해서 이미지를 변환해볼 것이다.
* */
@WebServlet("/transferToThumbnail")
public class TransferToThumbnailimageServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        /* 아래 구문을 출력해보면 Tomcat 컨테이너의 경로가 나타난다.
        *  그러나 우리는 테스트를 위해 소스 코드가 위치한 해당 프로젝트의 특정 위치를 기준으로 삼을 것이다.
        * */
        System.out.println("[PATH TEST]" + req.getSession().getServletContext().getRealPath("/WEB-INF"));

        /* 프로젝트 내 썸네일로 변경할 사진의 경로 및 이름을 절대 경로로 지정 */
        String originFilePath ="C:\\MyWs\\08_servlet\\chap13-file-io-lecture-source\\src\\main\\webapp\\WEB-INF\\origin-image\\cheesecake.png";
        File originFile = new File(originFilePath);

        /* 프로젝트 내 저장할 경로를 절대 경로로 지정 */
        String savePath = "C:\\MyWs\\08_servlet\\chap13-file-io-lecture-source\\src\\main\\webapp\\WEB-INF\\origin-image\\";

        File thumbnailFilePath = new File(savePath);
        // 썸네일 변환된 이미지 파일을 저장할 경로가 존재하지 않는다면 폴더 생성
        if(!thumbnailFilePath.exists()) {
            thumbnailFilePath.mkdirs();
        }
        // 썸네일 변환된 이미지 파일명 지정
        // 원본파일명_thumbnail.확장자
        String thumbnailFileName = "cheesecake_thumbnail.png";

        try {
            // 실제로 썸네일 파일이 변환되는 코드
            Thumbnails.of(originFile)   // 파일명
                    .size(600, 600)     // 파일 크기
                    .toFile(savePath + thumbnailFileName);  // 저장위치 + 어떻게 저장할 것인지

            System.out.println("[SAVED PATH]" + (thumbnailFilePath + thumbnailFileName));
            System.out.println("썸네일 변환 성공!!");
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
