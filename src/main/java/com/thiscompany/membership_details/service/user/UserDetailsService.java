package com.thiscompany.membership_details.service.user;

import com.thiscompany.membership_details.controller.dto.UserDetailsDTO;
import com.thiscompany.membership_details.controller.dto.generic.UserDetailsGeneric;
import com.thiscompany.membership_details.exception.UserNotFoundException;
import com.thiscompany.membership_details.camel.producer.RouteClient;
import com.thiscompany.membership_details.utils.CamelRouteDirects;
import com.thiscompany.membership_details.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserDetailsService {

    private final RouteClient routeClient;

    @Cacheable(cacheNames = "user-details")
    public UserDetailsDTO getUserDetails(long userId){
        Map<String, Object> headers = Map.of(
             "token", Utils.getTokenFromCurrentRequest(),
             "userId", userId
        );
        UserDetailsGeneric wrappedResponse = routeClient.requestBodyAndHeaders(
            CamelRouteDirects.GET_USER_DETAILS, null, headers, UserDetailsGeneric.class
        );
        if(wrappedResponse == null) {
            throw new UserNotFoundException(String.valueOf(userId));
        } else if(wrappedResponse.getResponse() == null || wrappedResponse.getResponse().isEmpty()){
            throw new UserNotFoundException(String.valueOf(userId));
        }
        UserDetailsDTO result = wrappedResponse.getResponse().getFirst();
        return result;
    }


}
