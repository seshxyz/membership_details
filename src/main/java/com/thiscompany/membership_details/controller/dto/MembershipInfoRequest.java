package com.thiscompany.membership_details.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public record MembershipInfoRequest(

        @JsonProperty(value = "user_id", required = true)
        @NotBlank(message = "user.id.is_empty")
        int userId,

        @JsonProperty(value = "group_id", required = true)
        @NotBlank(message = "group.id.is_empty")
        int groupId

) {
}
