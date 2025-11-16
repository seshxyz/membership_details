package com.thiscompany.membership_details.filter;

import com.thiscompany.membership_details.exception.TokenNotSpecifiedException;
import com.thiscompany.membership_details.utils.Utils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@Component
public class ServiceTokenPreFilter extends OncePerRequestFilter {

    @Qualifier("handlerExceptionResolver")
    private final HandlerExceptionResolver exceptionResolver;

    public ServiceTokenPreFilter(@Qualifier("handlerExceptionResolver") HandlerExceptionResolver exceptionResolver) {
        this.exceptionResolver = exceptionResolver;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (!"/auth".equals(request.getRequestURI())) {
            try {
                final String token = request.getHeader(Utils.Const.TOKEN_HEADER);
                if (token == null) {
                    throw new TokenNotSpecifiedException();
                } else if (token.isBlank()) {
                    throw new TokenNotSpecifiedException();
                }
                RequestContextHolder.currentRequestAttributes().setAttribute(Utils.Const.TOKEN_HEADER, token, RequestAttributes.SCOPE_REQUEST);
                filterChain.doFilter(request, response);
            } catch (TokenNotSpecifiedException e) {
                exceptionResolver.resolveException(request, response, null, e);
            }
        } else {
            filterChain.doFilter(request, response);
		}
    }
}
