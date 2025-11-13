package com.thiscompany.membership_details.exception.dto;

import com.fasterxml.jackson.annotation.JsonProperty;


public record VkApiErrorHolder(Error error) {

	public record Error (
		@JsonProperty(value = "error_code")
		int errorCode,

		@JsonProperty(value = "error_msg")
		String errorMessage

	) {}

}
