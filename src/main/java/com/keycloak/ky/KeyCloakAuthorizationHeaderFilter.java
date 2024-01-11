package com.keycloak.ky;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class KeyCloakAuthorizationHeaderFilter extends OncePerRequestFilter {

    private final TokenResponse tokenResponse;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
            String token = tokenResponse.getToken();
            HttpServletRequest modifiedRequest = token != null ? addTokenToHeader(request, token) : request;
            filterChain.doFilter(modifiedRequest, response);

    }

    private HttpServletRequest addTokenToHeader(HttpServletRequest request, String token) {
        return new HttpServletRequestWrapper(request) {
            @Override
            public String getHeader(String name) {
                if ("Authorization".equalsIgnoreCase(name)) {
                    return "Bearer " + token;
                }

                return super.getHeader(name);
            }
        };
    }
}
