package com.resolute.zero.security;

import com.resolute.zero.exceptions.AppException;
import com.resolute.zero.services.JWTutil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class JwtAuthenticatioFilter extends OncePerRequestFilter {

    public static final List<String> EXCLUDED_URLS = List.of("/login","/swagger-ui","/swagger-ui/index.html","/swagger-ui.html","/swagger-ui/","/swagger-ui/*","/favicon.ico","/","/src","/enquiry");
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        if (EXCLUDED_URLS.contains(requestURI)||requestURI.contains("/assets")) {
            filterChain.doFilter(request, response);
            return;
        }


        System.out.println(requestURI);

        String authHeader = request.getHeader("Authorization");
        String token = null;
        if(authHeader!=null&&authHeader.startsWith("Bearer ")){
            token = authHeader.substring(7);
        }
        if(token!=null&& JWTutil.validateToken(token)){
            String username = JWTutil.extractUsername(token);
            Authentication authentication = new UsernamePasswordAuthenticationToken(username,null, Collections.emptyList());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            filterChain.doFilter(request,response);
            return;
        }
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.sendError(HttpServletResponse.SC_FORBIDDEN, "Unauthorized Access");



    }
}
