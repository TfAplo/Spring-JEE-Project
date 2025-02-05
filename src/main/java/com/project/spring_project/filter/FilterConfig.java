package com.project.spring_project.filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<RequestRedirectFilter> loggingFilter() {
        FilterRegistrationBean<RequestRedirectFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new RequestRedirectFilter());
        registrationBean.addUrlPatterns("/*");
        return registrationBean;
    }
}
