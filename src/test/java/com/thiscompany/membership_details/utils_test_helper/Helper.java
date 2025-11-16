package com.thiscompany.membership_details.utils_test_helper;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class Helper {

	public static void mockServiceToken(){
		MockHttpServletRequest mockTokenHeaderRequestHolder = new MockHttpServletRequest();
		mockTokenHeaderRequestHolder.setAttribute("vk_service_token", "token token");
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(mockTokenHeaderRequestHolder));
	}

}
