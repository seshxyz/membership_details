package com.thiscompany.membership_details.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record MembershipInfoResponse(

        @JsonProperty(value = "first_name")
        String firstName,

        @JsonProperty(value = "last_name")
        String lastName,

        @JsonProperty(value = "nickname")
        String nickname,

        boolean member

) {
}
