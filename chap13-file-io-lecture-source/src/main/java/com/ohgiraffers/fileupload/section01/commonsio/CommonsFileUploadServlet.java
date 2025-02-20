package com.ohgiraffers.fileupload.section01.commonsio;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload2.core.DiskFileItemFactory;
import org.apache.commons.fileupload2.core.FileItem;
import org.apache.commons.fileupload2.jakarta.JakartaServletFileUpload;

import java.io.File;
import java.io.IOException;
import java.util.*;

@WebServlet("/commons/multi")
public class CommonsFileUploadServlet extends HttpServlet {

    private String rootLocation;
    private int maxFileSize;
    private String encodingType;

    @Override
    public void init() throws ServletException {

        /* 목차. 2. ServletContext 객체를 생성해 web.xml에 정의한 설정값을 소스 코드로 불러온다. */
        System.out.println("[CommonsFileUploadServlet] init 메서드 호출됨...");

        ServletContext context = getServletContext();

        rootLocation = context.getInitParameter("upload-location");
        maxFileSize = Integer.parseInt(context.getInitParameter("max-file-size"));
        encodingType = context.getInitParameter("encoding-type");

        System.out.println("2-1. 파일 저장 루트 경로(rootLocation): " + rootLocation);
        System.out.println("2-2. 업로드 파일 최대 용량(maxFileSize): " + maxFileSize);
        System.out.println("2-3. 인코딩 방식(encodingType): " + encodingType);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        System.out.println("[CommonsFileUploadServlet] doPost 메서드 호출됨...");

        /* 설명.
         *  commons-fileupload는 내부적으로 commons-io 라이브러리와 의존 관계하에 놓여있기 때문에 두 의존성 모두 추가해줘야 한다.
         *  여기서 <input type="file"> 태그가 한개이건 여러개이건,
         *  혹은, multiple 어트리뷰트가 있건 없건 상관 없이 모두 커버할 수 있도록 코드를 작성할 것이다.
         *  ===========================================================================================================
         *  multipart/form-data(멀티파트-폼 데이터) :
         *  form 태그 내부에 input 태그가 있을것인데, 이들이 멀트 파트 형식으로 인코딩되어 전송된다는 의미다.
         *  즉, 멀티파트 형식에서는 두 가지로 데이터를 구분할 수 있다.
         *  1. 파일인 폼 데이터
         *  2. 파일이 아닌 폼 데이터
         * */
        if (JakartaServletFileUpload.isMultipartContent(req)) {

            /* 목차. 3. 파일을 업로드 할 서버 경로를 설정하고, 해당 경로가 존재하지 않는다면 디렉터리를 생성한다. */
            String fileUploadDirectory = rootLocation + "/commons";

            File directory = new File(fileUploadDirectory);

            if (!directory.exists()) {
                System.out.println("파일 업로드 타겟 디렉토리 형성 : " + directory.mkdirs());    // make directory
            }

            /* 목차. 4. 아래는 최종적으로 request를 파싱한 폼 데이터를 종류별로 저장할 List와 Map 컬렉션이다.
             *  - List : 파일인 데이터들
             *  - Map : 파일이 아닌 데이터들
             * */
            Map<String, String> parameter = new HashMap<>();
            List<Map<String, String>> fileList = new ArrayList<>();

            /* 목차. 5. 파일을 업로드할 때 최대 크기나 임시 저장할 폴더의 경로 등을 포함하기 위한 인스턴스 생성 */
            DiskFileItemFactory.Builder fileItemFactory = new DiskFileItemFactory.Builder();

            fileItemFactory.setPath(new File(fileUploadDirectory).getPath());
            fileItemFactory.setBufferSize(maxFileSize);

            /* 목차. 6. 서블릿에서 기본 제공하는 기능 말고,
             *  꼭!! commons-fileupload2-jakarta 라이브러리에 있는 클래스로 import 해야 함. */
            JakartaServletFileUpload fileUpload = new JakartaServletFileUpload(fileItemFactory.get());

            try {
                /* 목차 7. Request를 파싱하여 데이터 하나 하나를 FileItem 인스턴스로 반환한다. */
                List<FileItem> fileItems = fileUpload.parseRequest(req);

                // 브라우저로부터 넘어온 데이터(파일) 확인해보기
                for (FileItem f : fileItems) {
                    System.out.println("f = " + f);
                }

                /* 목차. 8. 위에서 출력해본 모든 item들을 하나 씩 처리해보자. */
                for (int i = 0; i <= fileItems.size() - 1; i++) {

                    FileItem item = fileItems.get(i);   // 하나 꺼냄

                    /* 설명. 폼 데이터는 isFormField 속성이 true, 파일은 isFormField 속성이 false다. */
                    if (!item.isFormField()) {
                        // 파일 데이터를  처리하는 로직(List에 담을 것들)

                        /* 목차. 9. 파일의 사이즈가 0 byte인 것들은 의미가 없다.
                         *  전송된 파일이 존재하는 경우에만 처리하고, 크기가 0 byte인 경우에는 무시하도록 한다.
                         * */
                        if (item.getSize() > 0) {

                            /* 목차. 10. 파일 데이터의 원본 파일명과 input 태그의 name 어트리뷰트를 가져와서
                             *  저장할 파일명을 randomUUID()로 랜덤화 시킨다.
                             * */
                            String fieldName = item.getFieldName();     // input 태그의 name 어트리뷰트값
                            String originFileName = item.getName();     // input 태그의 value(파일명.확장자)값
                            System.out.println("fieldName = " + fieldName);
                            System.out.println("originFileName = " + originFileName);

                            int dotIndex = originFileName.lastIndexOf(".");
                            String extension = originFileName.substring(dotIndex);

                            String randomFileName = UUID.randomUUID().toString().replace("-", "") + extension;
                            System.out.println("dotIndex = " + dotIndex);
                            System.out.println("extension = " + extension);
                            System.out.println("randomFileName = " + randomFileName);

                            /* 목차. 11. 저장할 파일 정보를 담은 인스턴스를 생성한 후, 파일을 서버에 저장한다. */
                            System.out.println("여기에 저장될 예정... -> " + fileUploadDirectory + "/" + randomFileName);
                            File storeFile = new File(fileUploadDirectory + "/" + randomFileName);
                            item.write(storeFile.toPath());     // 이게 진짜 저장하는 부분!!

                            /* 목차. 12. DB에 저장할 정보들을 정리한다. */
                            Map<String, String> fileMap = new HashMap<>();

                            fileMap.put("fieldName", fieldName);            // 저장 파일명
                            fileMap.put("originFileName", originFileName);  // 원본 파일명
                            fileMap.put("savedFileName", randomFileName);   // 저장된 파일명(= 랜덤화된 파일명)
                            fileMap.put("savePath", fileUploadDirectory);   // 저장 경로

                            /* 설명. 지금 다루고 있는 item은 파일이기 때문에 초반부에 선언해둔 List에 추가한다. */
                            fileList.add(fileMap);
                        }

                    } else {
                        // 파일이 아닌 데이터를 처리하는 로직(Map에 담을 것들)

                        /* 목차 13.
                         *  전송된 폼의 name은 getFieldName()으로 받아오고, 해당 필드의 value는 getString()으로 받아온다.
                         *  하지만 인코딩 설정을 하더라도 전송되는 파라미터는 ISO-8859-1로 처리된다.
                         *  별도로 ISO-8859-1로 해석된 한글을 UTF-8로 변경해야 한다.
                         * */
//                        parameter.put(item.getFieldName(), item.getString());     // 인코딩 깨짐
                        parameter.put(item.getFieldName(), new String(item.getString().getBytes("ISO-8859-1"), "UTF-8"));
                    }
                }

                /* 설명. 파일인 데이터와 파일이 아닌 데이터를 각각 List와 Map에 담았고, 이를 마지막으로 확인해본다. */
                System.out.println("내가 담은 파일 데이터 : " + fileList);
                System.out.println("내가 담은 기타 데이터 : " + parameter);

            } catch (Exception e) {
                /* 목차. 14. 그 어떠한 종류의 Exception이 발생하더라도, 하나라도 실패한다면 모든 파일을 삭제해줘야 한다.
                 *  그래야 향후 DB와 연동시킨 트랜잭션 처리를 정상적으로 이끌어가고 풀어낼 수 있다.
                 * */

                int cnt = 0;

                for (int i = 0; i <= fileList.size() - 1; i++) {
                    Map<String, String> file = fileList.get(i);

                    File deleteFile = new File(fileUploadDirectory + "/" + file.get("savedFileName"));
                    boolean isDeleted = deleteFile.delete();        // 이게 진짜 삭제하는 부분!!

                    if (isDeleted)      // 성공적으로 삭제되었다면,
                        cnt ++;         // 카운트 올리기.
                }

                if (cnt == fileList.size())
                    System.out.println("업로드에 실패한 모든 사진 삭제 완료!");
                else
                    e.printStackTrace();
            }

            /* 목차. 15-1. 세션에 폼 데이터 정보를 저장하고 결과 화면을 출력하는 서블릿으로 redirect. */
            req.getSession().setAttribute("fileList", fileList);
            req.getSession().setAttribute("parameter", parameter);
        }

        // 목차 15-2-1. 서블릿으로 리다이렉트
        resp.sendRedirect("result");
        // 목차 15-2-2. JSP로 리다이렉트("/chap13" + "/jsp/uploadResult.jsp")
//        resp.sendRedirect(req.getContextPath() + "/jsp/uploadResult.jsp");
    }
}
