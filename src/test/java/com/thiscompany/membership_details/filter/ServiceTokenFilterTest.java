package com.thiscompany.membership_details.filter;

import com.thiscompany.membership_details.exception.TokenNotSpecifiedException;
import com.thiscompany.membership_details.utils.Utils;
import com.thiscompany.membership_details.utils_test_helper.Helper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ServiceTokenFilterTest {

	@Mock
	private ExceptionHandlerExceptionResolver exceptionHandlerExceptionResolver;

	@InjectMocks
	private ServiceTokenPreFilter serviceTokenPreFilter;

	private final static String AUTH_URI = "/auth";

	@Test
	void testServiceTokenFilter_TokenIsActuallyPresent_ContinueRequest() throws ServletException, IOException {
		MockHttpServletRequest request = new MockHttpServletRequest();
		MockHttpServletResponse response = new MockHttpServletResponse();
		FilterChain filterChain = mock(FilterChain.class);

		Helper.mockServiceToken();

		request.addHeader(Utils.Const.TOKEN_HEADER, "token token");

		assertDoesNotThrow(() -> serviceTokenPreFilter.doFilter(request, response, filterChain));

		verify(filterChain).doFilter(request, response);
	}


	@Test
	void testServiceTokenFilter_IfTokenIsNull_StopRequest_And_ThrowException() throws ServletException, IOException {
		FilterChain filterChain = mock(FilterChain.class);
		MockHttpServletRequest request = new MockHttpServletRequest();
		MockHttpServletResponse response = new MockHttpServletResponse();

		serviceTokenPreFilter.doFilter(request, response, filterChain);

		verify(exceptionHandlerExceptionResolver)
			.resolveException(eq(request), eq(response), isNull(), any(TokenNotSpecifiedException.class));
	}

	@Test
	void testServiceTokenFilter_IfTokenIsEmpty_StopRequest_And_ThrowException() throws ServletException, IOException {
		FilterChain filterChain = mock(FilterChain.class);
		MockHttpServletRequest request = new MockHttpServletRequest();
		MockHttpServletResponse response = new MockHttpServletResponse();

		request.addHeader(Utils.Const.TOKEN_HEADER, "");
		serviceTokenPreFilter.doFilter(request, response, filterChain);

		verify(exceptionHandlerExceptionResolver)
			.resolveException(eq(request), eq(response), isNull(), any(TokenNotSpecifiedException.class));
	}

	@Test
	void setServiceTokenFilter_IfUriIsAuth_ThenSkipFilter() throws ServletException, IOException {
		FilterChain filterChain = mock(FilterChain.class);
		MockHttpServletRequest request = new MockHttpServletRequest();
		MockHttpServletResponse response = new MockHttpServletResponse();

		request.setRequestURI(AUTH_URI);

		serviceTokenPreFilter.doFilter(request, response, filterChain);

		verify(filterChain).doFilter(request, response);
	}

}
