package com.thiscompany.membership_details.utils;

import com.thiscompany.membership_details.exception_handler.EmptyTokenHeaderException;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.Optional;

public class Utils {

    private Utils() {}

    public static class Const {

        public final static String TOKEN_HEADER = "vk_service_token";

        public final static String BEARER_HEADER = "Bearer ";

        public final static String VK_BASE_URL = "api.vk.ru/method";
    }

    public static String getTokenFromCurrentRequest() {
        return Optional.ofNullable(RequestContextHolder.getRequestAttributes())
             .map(attributes -> attributes.getAttribute(Utils.Const.TOKEN_HEADER, RequestAttributes.SCOPE_REQUEST))
             .map(Object::toString)
             .orElseThrow(() -> new EmptyTokenHeaderException(new Object[]{Utils.Const.TOKEN_HEADER}));
    }

}
