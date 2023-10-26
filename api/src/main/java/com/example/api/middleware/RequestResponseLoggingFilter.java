package com.example.api.middleware;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.springframework.core.Ordered.HIGHEST_PRECEDENCE;

@Component
@Order(HIGHEST_PRECEDENCE)
@Slf4j
public class RequestResponseLoggingFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String fullUri =
            request.getRequestURI() + (StringUtils.isEmpty(request.getQueryString()) ? "" :
                "?" + request.getQueryString());
        log.info("start request {} - [{}]", request.getMethod(), fullUri);

        filterChain.doFilter(request, response);
        log.info("done request [{}] with response status {}", fullUri,
            response.getStatus());
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return "/".equals(request.getRequestURI()) || request.getRequestURI().contains("actuator");
    }

}
