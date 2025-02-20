package com.ohgiraffers.filter.section01.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;

import java.io.IOException;

/* WebFilter 어노테이션에 URL 경로를 명시하여 해당 요청 시 filter가 동작하게 설정한다.
*  만약 URL 패턴에 "/*"와 같이 작성한다면 이는 와일드 카드로 모든 요청 경로를 의미한다.
* */
@WebFilter("/first/*")
public class FirstFilter implements Filter {

    /* 기본생성자 : 필터의 경우, 컨테이너(=tomcat)를 Run하는 시점에 인스턴스를 미리 생성한다. */
    public FirstFilter() {
        System.out.println("[FirstFilter](기본생성자) 호출됨...");
    }

    /* init() : 필터 인스턴스가 최초 생성될 때 호출되는 메서드 */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("[FirstFilter](init) 호출됨...");
    }

    /* doFilter() : 다음 서블릿(또는 필터)로 진행시키기 전에 전처리할 코드를 작성 */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        System.out.println("[FirstFilter](doFilter) 호출됨...");

        System.out.println("[FirstFilter](doFilter) 다음 서블릿(또는 필터)로 요청 흐름 전달...");

        // FilterChain에서 제공하는 doFilter() 메서드를 사용해 다음 서블릿(또는 필터)로 진행시킬 수 있다.
        chain.doFilter(request, response);

        System.out.println("[FirstFilter](doFilter) 서블릿(또는 필터) 요청 수행 완료...");
    }

    /* destroy() : 필터 인스턴스가 소멸될 때 호출(= tomcat 종료 시) */
    @Override
    public void destroy() {
        System.out.println("[FirstFilter](destroy) 호출됨...");
    }
}
