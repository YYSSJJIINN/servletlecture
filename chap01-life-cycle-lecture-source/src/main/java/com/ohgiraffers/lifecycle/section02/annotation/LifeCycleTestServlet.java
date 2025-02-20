package com.ohgiraffers.lifecycle.section02.annotation;

import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;

import java.io.IOException;
/* Annotation 방식으로 서블릿을 등록하고 URL 매핑을 전의할 수 있다.
* @WebServlet 어노테이션을 클래스 레벨에 정의하면 이제부터 해당 클래스는
* 서블릿 컨테이너가 인식할 수 있는 서블릿으로써 등록된다.
* - name : 해당 서블릿을 등록할 때 사용할 이름
* - value : 해당 서블릿이 호출될 때 매핑될 URL 패턴
* */
@WebServlet(value = "/annotation-lifecycle", name = "annotationmapping", loadOnStartup = 100)
public class LifeCycleTestServlet extends HttpServlet {

    /* 각 메서드의 호출 횟수를 카운트 할 목적의 필드 */
    private int initCount = 1;
    private int serviceCount = 1;
    private int destroyCount = 1;

    /* 기본생성자 */
    public LifeCycleTestServlet() {
        System.out.println("Annotation 방식 - 기본생성자 호출됨.");
    }

    /* init() : 최초 서블릿 요청 시 동작 */
    @Override
    public void init() throws ServletException {
        System.out.println("Annotation 방식 - init() 메서드 호출됨. : " + initCount++);
    }

    /* service() : 서블릿 컨테이너에 의해 호출되고 최초 요청 시에는 init() 이후에,
    * 두 번째 요청부터는 init() 호출 없이 바로 해당 메서드가 호출됨.
    * */
    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        System.out.println("Annotation 방식 - service() 메서드 호출됨. : " + serviceCount++);
    }

    /* destroy() : 컨테이너가 종료될 때 호출되는 메서드(자원 반납 용도) */
    @Override
    public void destroy() {
        System.out.println("Annotation 방식 - destroy() 메서드 호출됨. : " + destroyCount++);
    }

    /* note. Redeploy vs. Restart Server
     *  ---------------------------------------------------------------------------------------------------------
     *  1. Redeploy
     *   - 웹 애플리케이션을 (TomCat)서버에서 제거한 후, 동일한 웹 애플리케이션을 'Re-Build'하여 서버에 다시 배포하는 과정.
     *   - 코드나 리소스 파일의 변경 사항을 적용할 때 사용.
     *   - 서버를 재시작 하는 것이 아닌, 특정 애플리케이션의 변경 사항만 적용할 수 있으나
     *     재배포 과정에서 메모리 누수가 발생할 수 있어 가끔은 Server Restart를 실행해줘야 함.
     *  ---------------------------------------------------------------------------------------------------------
     *  2. Restart Server
     *   - Tomcat 서버를 완전히 중단했다가 다시 실행하는 것을 의미.
     *   - 애플리케이션들을 포함한 서버 전체를 초기화하기 때문에 메모리 누수나 기타 리소스 관련 문제가 발생하지 않음.
     *   - 서버 재시작 시, 서버가 관리중인 모든 애플리케이션에 대해 접근 및 사용 불가능.
     * */
}

