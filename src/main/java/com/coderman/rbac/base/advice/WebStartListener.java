package com.coderman.rbac.base.advice;

import org.springframework.beans.factory.annotation.Value;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class WebStartListener implements ServletContextListener {


    //github
    @Value("${github.client.id}")
    private String clientId;

    @Value("${github.client.secret}")
    private String clientSecret;

    @Value("${github.client.redirecturi}")
    private String RedirectUri;

    @Value("${index.loginUrl}")
    private String loginUrl;


    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        //登入的地址
        loginUrl=loginUrl+"?client_id="+clientId+"&redirect_uri="+RedirectUri+"&scope=user&state=1";
        ServletContext servletContext = servletContextEvent.getServletContext();
        servletContext.setAttribute("gitHubLoginUrl",loginUrl);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
