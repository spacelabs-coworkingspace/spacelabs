package com.rawlabs.spacelabs.config;

import com.rawlabs.spacelabs.component.TokenProvider;
import com.rawlabs.spacelabs.domain.dao.User;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

import static com.rawlabs.spacelabs.component.UnauthorizedEntryPoint.X_ERROR_MESSAGE;

@Slf4j
public class JwtAuthenticationFilter extends GenericFilterBean {

    private final UserDetailsService userDetailsService;
    private final TokenProvider tokenProvider;

    public JwtAuthenticationFilter(UserDetailsService userDetailsService, TokenProvider tokenProvider) {
        this.userDetailsService = userDetailsService;
        this.tokenProvider = tokenProvider;
    }


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        String token = null;
        String username = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        final String BEARER = "Bearer ";
        if (authorization != null && authorization.startsWith(BEARER)) {
            token = authorization.replace(BEARER, "");

            try {
                username = tokenProvider.getUsername(token);
            } catch (IllegalArgumentException e) {
                log.error("An error occurred during getting username from token. Error: {}", e.getMessage());
                response.addHeader(X_ERROR_MESSAGE, "Invalid token!");
            } catch (ExpiredJwtException e) {
                log.error("Token is expired. Error: {}", e.getMessage());
                response.addHeader(X_ERROR_MESSAGE, "Expired token!");
            } catch (SignatureException e) {
                log.error("Authentication Failed. Username or Password not valid. Error: {}", e.getMessage());
                response.addHeader(X_ERROR_MESSAGE, "Invalid token!");
            }
        }

        if (username != null && authentication == null) {
            User user = (User) userDetailsService.loadUserByUsername(username);

            if (tokenProvider.isTokenValid(token, user)) {
                Authentication authenticationToken = tokenProvider.getAuthenticationToken(user);
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }

        filterChain.doFilter(request, response);
    }
}
