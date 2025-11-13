package com.thiscompany.membership_details.camel.producer;

import lombok.RequiredArgsConstructor;
import org.apache.camel.ProducerTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class RouteClient {

	private final ProducerTemplate producerTemplate;

	public <T> T requestBodyAndHeaders(String endpointUri, Object body, Map<String, Object> headers, Class<T> response) {
		return producerTemplate.requestBodyAndHeaders(
			endpointUri, body, headers, response
		);
	}

}
