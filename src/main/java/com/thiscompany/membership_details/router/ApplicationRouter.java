package com.thiscompany.membership_details.router;

import com.thiscompany.membership_details.controller.dto.generic.GenericResponse;
import com.thiscompany.membership_details.utils.Utils;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class ApplicationRouter extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("direct://getUserFullname")
             .setHeader("Content-Type", constant("application/json"))
             .setHeader("Authorization", simple(Utils.Const.BEARER_HEADER+"${header.token}"))
             .removeHeader("token")
             .toD("https://"+Utils.Const.VK_BASE_URL+"/users.get?user_id=${header.userId}&fields=nickname&v=5.199")
             .unmarshal()
             .json(GenericResponse.class);

        from("direct://isUserMember")
             .setHeader("Content-Type", constant("application/json"))
             .setHeader("Authorization", simple(Utils.Const.BEARER_HEADER+"${header.token}"))
             .removeHeader("token")
             .toD("https://"+Utils.Const.VK_BASE_URL+"/groups.isMember?group_id=${header.groupId}&user_id=${header.userId}&v=5.199")
             .unmarshal()
             .json(GenericResponse.class);
    }
}
