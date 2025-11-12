package com.thiscompany.membership_details.service;

import com.thiscompany.membership_details.controller.dto.generic.BooleanGeneric;
import com.thiscompany.membership_details.controller.dto.MembershipInfoRequest;
import com.thiscompany.membership_details.utils.HeadersBuilder;
import com.thiscompany.membership_details.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.apache.camel.ProducerTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class GroupMembershipChecker {

    private final ProducerTemplate producerTemplate;

    public boolean checkUserMembership(MembershipInfoRequest userInfoRequest) {
        Map<String, Object> headers = HeadersBuilder.create()
             .add("token", Utils.getTokenFromCurrentRequest())
             .add("userId", userInfoRequest.userId())
             .add("groupId", userInfoRequest.groupId())
             .build();
        BooleanGeneric response = producerTemplate.requestBodyAndHeaders(
             "direct://isUserMember", null, headers, BooleanGeneric.class
        );
        return response.getResponse().booleanValue();
    }

}
