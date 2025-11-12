package com.thiscompany.membership_details.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public record MembershipInfoRequest(

        @JsonProperty(value = "user_id")
        @NotBlank(message = "user.id.is_empty")
        int userId,

        @JsonProperty(value = "group_id")
        @NotBlank(message = "group.id.is_empty")
        int groupId

) {
}
