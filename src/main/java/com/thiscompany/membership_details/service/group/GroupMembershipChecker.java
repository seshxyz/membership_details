package com.thiscompany.membership_details.service.group;

import com.thiscompany.membership_details.controller.dto.generic.BooleanGeneric;
import com.thiscompany.membership_details.camel.producer.RouteClient;
import com.thiscompany.membership_details.utils.CamelRouteDirects;
import com.thiscompany.membership_details.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class GroupMembershipChecker {

    private final RouteClient routeClient;

    @Cacheable(cacheNames = "membership-details", key = "#userId + ':' + #groupId")
    public boolean checkUserMembership(int userId, long groupId) {
        Map<String, Object> headers = Map.of(
            "token", Utils.getTokenFromCurrentRequest(),
            "userId", userId,
            "groupId", groupId
        );
        BooleanGeneric response = routeClient.requestBodyAndHeaders(
            CamelRouteDirects.GET_IS_MEMBER, null, headers, BooleanGeneric.class
        );
        Boolean result = response.getResponse();
        return result;
    }

}
