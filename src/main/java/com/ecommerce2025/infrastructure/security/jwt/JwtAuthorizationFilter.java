package com.ecommerce2025.infrastructure.security.jwt;


import com.ecommerce2025.infrastructure.security.service.UserDetailService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.UnavailableException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.charset.MalformedInputException;

import static com.ecommerce2025.infrastructure.security.jwt.JWTValidated.*;

@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    public JwtAuthorizationFilter(UserDetailService userDetailService) {
        this.userDetailService = userDetailService;
    }

    private final UserDetailService userDetailService;




    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
     try{
         if(tokenExists(request, response)){
             Claims claims = JWTValid(request);
             if (claims.get("authorities") != null){
                 setAuthentication(claims, userDetailService);
             }else
             {
                 SecurityContextHolder.clearContext();
             }
         }else{
             SecurityContextHolder.clearContext();
         }
         filterChain.doFilter(request, response);
     }catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException e){
         response.setStatus(HttpServletResponse.SC_FORBIDDEN);
         response.sendError(HttpServletResponse.SC_FORBIDDEN);
         return;

     }
    }
}
