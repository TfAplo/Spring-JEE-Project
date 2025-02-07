package com.project.spring_project.filter;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.AnonymousAuthenticationToken;

@WebFilter("/*")  // Applique ce filtre à toutes les requêtes
public class RequestRedirectFilter implements Filter {



    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }



    @Override
    public void doFilter(jakarta.servlet.ServletRequest request, jakarta.servlet.ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;


        if (isUserAuthenticated() && isRestrictedPage(httpRequest)) {
            httpResponse.sendRedirect("/home");  // Redirection vers /home
        } else {
            chain.doFilter(request, response);  // Continuer avec la chaîne de filtres
        }
    }

    private boolean isUserAuthenticated() {
        return SecurityContextHolder.getContext().getAuthentication() != null &&
                SecurityContextHolder.getContext().getAuthentication().isAuthenticated() &&
                !(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken);
    }

    private boolean isRestrictedPage(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        return !(requestURI.equals("/home") || requestURI.equals("/test") || requestURI.equals("/styles.css") || requestURI.equals("/styleHomePage.css"));
    }

    @Override
    public void destroy() {
    }
}
