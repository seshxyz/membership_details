package com.thiscompany.membership_details.controller.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record UserFullNameDTO(

        @JsonProperty(value = "first_name")
        String firstName,

        @JsonProperty(value = "last_name")
        String lastName,

        @JsonProperty(value = "nickname")
        String nickname

) {
}
