package com.thiscompany.membership_details.utils;

public final class ExternalApiConst {

	private ExternalApiConst() {}

	public static class Url {

		private Url() {}

		public final static String VK_BASE_URL = "https://api.vk.ru/method";
	}

	public static class Path {

		private Path() {}

		public static final String GET_USER_DETAILS_PATH = "/users.get?user_id=${header.userId}&fields=nickname&v=5.199";

		public static final String GET_IS_MEMBER = "/groups.isMember?group_id=${header.groupId}&user_id=${header.userId}&v=5.199";
	}


}
