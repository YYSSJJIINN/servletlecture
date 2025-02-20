package com.ohgiraffers.listener.section01.contextlistener;

import jakarta.servlet.ServletContextAttributeEvent;
import jakarta.servlet.ServletContextAttributeListener;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class ContextListnerTest
        implements ServletContextListener, ServletContextAttributeListener {

    /* Tomcat 기동 이후 context가 생성될 때 변화를 감지하는 Listener 인스턴스가 함께 생성된다. */
    public ContextListnerTest() {
        System.out.println("[ContextListnerTest] 기본생성자 호출됨 - 컨텍스트 인스턴스 생성");
    }

    /* context가 최초로 생성될 떄, 생성자 호출 이후 동작하는 메서드다.*/
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("[ContextListnerTest] contextInitialized 호출됨 - 컨텍스트 초기화됨");
    }

    /* Tomcat 정지 시, context가 소멸할 때 동작하는 메서드다. */
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("[ContextListnerTest] contextDestroyed 호출됨 - 컨텍스트 소멸됨");
    }

    // ----------------------------------------------------------------------------

    /* context에 어트리뷰트가 추가될 때 동작 */
    @Override
    public void attributeAdded(ServletContextAttributeEvent event) {
        System.out.println("[ContextListnerTest] attributeAdded 호출됨 - 어트리뷰트값 추가!");

        System.out.println(event.getName() + " : " + event.getValue());
        /* Tomcat을 동작시키자 마자 3개의 어트리뷰트가 추가되는 것을 확인할 수 있다.
        *  org.apache.jasper.runtime.JspApplicationContextImpl : JSP를 실행시키기 위한 컨텍스트
        *  org.apache.jasper.compiler.ELInterpreter : JSP 페이지에서 EL 표현식을 처리하기 위한 표현식 해석기
        *  org.apache.jasper.compiler.StringInterpreter : JSP의 문자열 처리를 위한 문자열 해석기
        * */
    }

    /* context에 어트리뷰트가 수정될 때 동작 */
    @Override
    public void attributeReplaced(ServletContextAttributeEvent event) {
        System.out.println("[ContextListnerTest] attributeReplaced 호출됨 - 어트리뷰트값 수정!");

        System.out.println(event.getName() + " : " + event.getValue());
    }

    @Override
    public void attributeRemoved(ServletContextAttributeEvent event) {
        System.out.println("[ContextListnerTest] attributeRemoved 호출됨 - 어트리뷰트값 삭제!");

        System.out.println(event.getName() + " : " + event.getValue());
    }
}
