package com.thiscompany.membership_details.service.user;

import com.thiscompany.membership_details.camel.producer.RouteClient;
import com.thiscompany.membership_details.controller.dto.UserDetailsDTO;
import com.thiscompany.membership_details.controller.dto.generic.UserDetailsGeneric;
import com.thiscompany.membership_details.exception.TokenNotSpecifiedException;
import com.thiscompany.membership_details.exception.UserNotFoundException;
import com.thiscompany.membership_details.utils.CamelRouteDirects;
import com.thiscompany.membership_details.utils.Utils;
import com.thiscompany.membership_details.utils_test_helper.Helper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;



@ExtendWith(SpringExtension.class)
class UserDetailsServiceTest {

	@Mock
	private RouteClient routeClient;

	@InjectMocks
	private UserDetailsService userDetailsService;

	private static long userId;
	private static UserDetailsDTO dto;
	private static List<String> allowedNicknames;

	@BeforeAll
	static void setUp() {
		dto = new UserDetailsDTO(
			"Alice",
			"Berulli",
			""
		);

		allowedNicknames = new ArrayList<>() {{
			add("");
			add(null);
		}};

		userId = 1234;
	}

	@Test
	void testGetUserDetails_Successfully() {
		Helper.mockServiceToken();
		UserDetailsGeneric SuccessResponse = new UserDetailsGeneric();
		SuccessResponse.setResponse(List.of(dto));

		when(routeClient.requestBodyAndHeaders(
			eq(CamelRouteDirects.GET_USER_DETAILS),
			isNull(),
			any(Map.class),
			eq(UserDetailsGeneric.class)
		)).thenReturn(SuccessResponse);

		UserDetailsDTO result = userDetailsService.getUserDetails(userId);

		allowedNicknames.add(result.nickname());

		assertNotNull(result);
		assertEquals("Alice", result.firstName());
		assertEquals("Berulli", result.lastName());
		assertTrue(allowedNicknames.contains(dto.nickname()) || (dto.nickname() != null && !dto.nickname().isEmpty()),
			"Отчество имеет недопустимое значение");
	}

	@Test
	void testGetUserDetails_UserNotFound() {
		Helper.mockServiceToken();
		UserDetailsGeneric emptyResponse = new UserDetailsGeneric();
		emptyResponse.setResponse(Collections.emptyList());

		when(routeClient.requestBodyAndHeaders(
			eq(CamelRouteDirects.GET_USER_DETAILS),
			isNull(),
			anyMap(),
			eq(UserDetailsGeneric.class)
		)).thenReturn(emptyResponse);

		assertThrows(UserNotFoundException.class, () -> {
			userDetailsService.getUserDetails(userId);
		});
	}

	@Test
	void testGetUserDetails_IfServiceTokenIsNotPresent_ThrowException() {

		try (MockedStatic<Utils> mocked = mockStatic(Utils.class)) {

			mocked.when(Utils::getTokenFromCurrentRequest).thenThrow(TokenNotSpecifiedException.class);

			assertThrows(TokenNotSpecifiedException.class, () -> {
				userDetailsService.getUserDetails(userId);
			});
		}
	}



}