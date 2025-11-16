package com.thiscompany.membership_details.utils;

import com.thiscompany.membership_details.exception.TokenNotSpecifiedException;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.Optional;

public final class Utils {

    private Utils() {}

    public static class Const {

        private Const() {}

        public static final String TOKEN_HEADER = "vk_service_token";

        public static final String AUTH_HEADER = "Authorization";
    }

    public static String getTokenFromCurrentRequest() {
        return Optional.ofNullable(RequestContextHolder.getRequestAttributes())
             .map(attributes -> attributes.getAttribute(Utils.Const.TOKEN_HEADER, RequestAttributes.SCOPE_REQUEST))
             .map(Object::toString)
             .filter(token ->!token.isBlank())
             .orElseThrow(TokenNotSpecifiedException::new);
    }

}
