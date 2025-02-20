package com.ohgiraffers.lifecycle.section01.xml;

import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServlet;

import java.io.IOException;

/* 서블릿이 되기 위해서는 HttpServlet 클래스를 상속받아야 한다. */
public class LifeCycleTestServlet extends HttpServlet {

    /* 각 메서드의 호출 횟수를 카운트 할 목적의 필드 */
    private int initCount = 1;
    private int serviceCount = 1;
    private int destroyCount = 1;

    /* 기본생성자 */
    public LifeCycleTestServlet() {
        System.out.println("XML 방식 - 기본생성자 호출됨.");
    }

    /* init() : 최초 서블릿 요청 시 동작 */
    @Override
    public void init() throws ServletException {
        System.out.println("XML 방식 - init() 메서드 호출됨. : " + initCount++);
    }

    /* service() : 서블릿 컨테이너에 의해 호출되고 최초 요청 시에는 init() 이후에,
    * 두 번째 요청부터는 init() 호출 없이 바로 해당 메서드가 호출됨.
    * */
    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        System.out.println("XML 방식 - service() 메서드 호출됨. : " + serviceCount++);
    }

    /* destroy() : 컨테이너가 종료될 때 호출되는 메서드(자원 반납 용도) */
    @Override
    public void destroy() {
        System.out.println("XML 방식 - destroy() 메서드 호출됨. : " + destroyCount++);
    }
}

