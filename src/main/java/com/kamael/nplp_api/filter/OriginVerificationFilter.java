package com.kamael.nplp_api.filter;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpMethod; 

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class OriginVerificationFilter extends OncePerRequestFilter {

    private static final String ALLOWED_ORIGIN = "http://localhost:4200";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String origin = request.getHeader("Origin");

        if ("/result/create".equals(request.getServletPath()) && 
            HttpMethod.POST.matches(request.getMethod())) {

            if (!ALLOWED_ORIGIN.equals(origin)) {
                response.sendError(HttpStatus.FORBIDDEN.value(), "Origin not allowed");
                return;
            }
        }

		filterChain.doFilter(request, response);
    }
}
