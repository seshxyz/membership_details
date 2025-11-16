package com.thiscompany.membership_details.service.group;

import com.thiscompany.membership_details.camel.producer.RouteClient;
import com.thiscompany.membership_details.controller.dto.generic.BooleanGeneric;
import com.thiscompany.membership_details.exception.TokenNotSpecifiedException;
import com.thiscompany.membership_details.utils.CamelRouteDirects;
import com.thiscompany.membership_details.utils.Utils;
import com.thiscompany.membership_details.utils_test_helper.Helper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GroupMembershipCheckerTest {

	@Mock
	private RouteClient routeClient;

	@InjectMocks
	private GroupMembershipChecker groupMembershipChecker;

	private static int userId;
	private static int groupId;

	@BeforeAll
	static void setup() {
		userId = 456;
		groupId = 123;
	}

	@ParameterizedTest
	@ValueSource(booleans = {true, false})
	void testCheckUserMembership_Successfully_IfResponseAnyBoolean(boolean responseBoolean) {
		BooleanGeneric successResponse = new BooleanGeneric();
		successResponse.setResponse(responseBoolean);

		when(routeClient.requestBodyAndHeaders(
			eq(CamelRouteDirects.GET_IS_MEMBER),
			isNull(),
			anyMap(),
			eq(BooleanGeneric.class)
		)).thenReturn(successResponse);

		Helper.mockServiceToken();

		boolean result = groupMembershipChecker.checkUserMembership(userId, groupId);

		assertEquals(responseBoolean, result);
	}

	@Test
	void testCheckUserMembership_IfTokenIsNotPresent_ThrowException() {
		try (MockedStatic<Utils> mocked = mockStatic(Utils.class)) {

			mocked.when(Utils::getTokenFromCurrentRequest).thenThrow(TokenNotSpecifiedException.class);

			assertThrows(TokenNotSpecifiedException.class, () -> {
				groupMembershipChecker.checkUserMembership(userId, groupId);
			});
		}
	}
}