package com.thiscompany.membership_details.camel.processor;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thiscompany.membership_details.controller.dto.generic.GenericResponse;
import com.thiscompany.membership_details.exception.ExternalApiError;
import com.thiscompany.membership_details.exception.dto.VkApiErrorHolder;
import lombok.RequiredArgsConstructor;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.converter.stream.InputStreamCache;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class ExternalApiResponseProcessor implements Processor {

	private final ObjectMapper objectMapper;

	@Override
	public void process(Exchange exchange) throws Exception {
		JsonNode body = objectMapper.readTree(exchange.getIn().getBody(InputStreamCache.class));
		if (body.has("error")) {
			VkApiErrorHolder.Error error = objectMapper.treeToValue(body, VkApiErrorHolder.class).error();
			throw new ExternalApiError(
				error.errorCode(),
				error.errorMessage()
			);
		}
		GenericResponse<?> response = objectMapper.treeToValue(body, GenericResponse.class);
		exchange.getIn().setBody(response);
	}

}