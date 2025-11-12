package com.thiscompany.membership_details.utils;

import java.util.HashMap;
import java.util.Map;

public class HeadersBuilder {

    private final Map<String, Object> headers = new HashMap<>();

    public static HeadersBuilder create() {
        return new HeadersBuilder();
    }

    public HeadersBuilder add(String key, Object value) {
        headers.put(key, value);
        return this;
    }

    public Map<String, Object> build() {
        return headers;
    }

}
