package com.aquent.crudapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import javax.validation.Validator;

/**
 * Replaced XML Spring config files with Boot application config
 * application-beans.xml
 * dispatcher-servlet.xml
 */
@SpringBootApplication
public class CrudApplication extends SpringBootServletInitializer implements WebMvcConfigurer {

    public static void main(String[] args) {
        SpringApplication.run(CrudApplication.class, args);
    }

    /**
     * Tell Spring where to look for our JSPs.
     *
     * @return
     */
    @Bean
    public ViewResolver viewResolver() {
        InternalResourceViewResolver internalResourceViewResolver = new InternalResourceViewResolver();
        internalResourceViewResolver.setPrefix("/WEB-INF/jsp/");
        internalResourceViewResolver.setSuffix(".jsp");
        return internalResourceViewResolver;
    }

    /**
     * Map JS and CSS resources. This allows the files to be served up by the servlet
     *
     * @param registry Spring resource registry class
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //registry.addResourceHandler("/css/**")
        //        .addResourceLocations("/WEB-INF/css/");
        registry.addResourceHandler("/js/**")
                .addResourceLocations("/WEB-INF/js/");
    }

    /**
     * Configure the Validator as LocalValidatorFactoryBean as was speicified in the application-beans.xml
     *
     * @return Bean
     */
    @Bean
    public Validator validator() {
        return new org.springframework.validation.beanvalidation.LocalValidatorFactoryBean();
    }
}
