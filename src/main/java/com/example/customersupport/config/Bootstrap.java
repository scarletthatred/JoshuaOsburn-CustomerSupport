package com.example.customersupport.config;

import jakarta.servlet.MultipartConfigElement;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRegistration;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class Bootstrap implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext container) throws ServletException {
        AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
        rootContext.register(RootContextConfig.class);
        container.addListener(new ContextLoaderListener(rootContext));

        // get application context config and setup dispatcher form spring
        AnnotationConfigWebApplicationContext servletContext=new AnnotationConfigWebApplicationContext();
        servletContext.register(ServletContextConfig.class);
        ServletRegistration.Dynamic dispatcher = container.addServlet("springDisspatcher", new DispatcherServlet(servletContext));
        dispatcher.setLoadOnStartup(1);
        //fileSizeThreshold = 5_242_880, maxFileSize = 20971520L, maxRequestSize = 41_943_40L
        dispatcher.setMultipartConfig(new MultipartConfigElement(null,20971520L,41_943_40L,5_242_880));
        dispatcher.addMapping("/");
    }
}
