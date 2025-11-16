package com.thiscompany.membership_details.utils;

import com.thiscompany.membership_details.exception.TokenNotSpecifiedException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.ValueSources;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

class UtilsTest {

	@Test
	void getTokenFromCurrentRequest_IfSuccessWithAnyValueType_ReturnTokenValue() {
		RequestAttributes requestAttributes = Mockito.mock(RequestAttributes.class);
		String value = "value1234";

		when(requestAttributes.getAttribute(Utils.Const.TOKEN_HEADER, RequestAttributes.SCOPE_REQUEST))
			.thenReturn(value);

		try(MockedStatic<RequestContextHolder> mockedStatic = Mockito.mockStatic(RequestContextHolder.class)) {
			mockedStatic.when(RequestContextHolder::currentRequestAttributes).thenReturn(requestAttributes);

		Object result = RequestContextHolder.currentRequestAttributes().getAttribute(Utils.Const.TOKEN_HEADER, RequestAttributes.SCOPE_REQUEST);

			assertDoesNotThrow(TokenNotSpecifiedException::new);
			assertEquals(value, result);
		}
	}

	@ParameterizedTest
	@NullSource
	@ValueSources(
		@ValueSource(strings = {"", " "})
	)
	void getTokenFromCurrentRequest_IfFailureThrowException(Object value) {
		RequestAttributes requestAttributes = Mockito.mock(RequestAttributes.class);

		when(requestAttributes.getAttribute(Utils.Const.TOKEN_HEADER, RequestAttributes.SCOPE_REQUEST))
			.thenReturn(null);

		try(MockedStatic<RequestContextHolder> mockedStatic = Mockito.mockStatic(RequestContextHolder.class)) {
			mockedStatic.when(RequestContextHolder::currentRequestAttributes).thenThrow(TokenNotSpecifiedException.class);

			assertThrows(TokenNotSpecifiedException.class, () -> RequestContextHolder.currentRequestAttributes()
																	 .getAttribute(Utils.Const.TOKEN_HEADER, RequestAttributes.SCOPE_REQUEST));
		}
	}
}