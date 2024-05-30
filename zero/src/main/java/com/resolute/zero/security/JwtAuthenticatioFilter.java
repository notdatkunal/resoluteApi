package com.resolute.zero.security;

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

    public static final List<String> EXCLUDED_URLS = List.of("/login");
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        if (EXCLUDED_URLS.contains(requestURI)) {
            filterChain.doFilter(request, response);
            return;
        }
        String authHeader = request.getHeader("Authorization");
        String token = null;
        if(authHeader!=null&&authHeader.startsWith("Bearer ")){
            token = authHeader.substring(7);
        }
        System.out.println("header found"+authHeader);
        if(token!=null&& JWTutil.validateToken(token)){
            String username = JWTutil.extractUsername(token);
            Authentication authentication = new UsernamePasswordAuthenticationToken(username,null, Collections.emptyList());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            filterChain.doFilter(request,response);
        }
        System.out.println("token validated "+token);
    }
}
