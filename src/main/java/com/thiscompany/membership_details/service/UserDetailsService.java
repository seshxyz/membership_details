package com.thiscompany.membership_details.service;

import com.thiscompany.membership_details.controller.dto.UserFullNameDTO;
import com.thiscompany.membership_details.controller.dto.generic.UserFullNameGeneric;
import com.thiscompany.membership_details.exception_handler.UserNotFoundException;
import com.thiscompany.membership_details.utils.HeadersBuilder;
import com.thiscompany.membership_details.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.apache.camel.ProducerTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDetailsService {

    private final ProducerTemplate producerTemplate;

    public UserFullNameDTO getUserFullname(int userId){
        String token = Utils.getTokenFromCurrentRequest();
        Map<String, Object> headers = HeadersBuilder.create()
             .add("token", token)
             .add("userId", userId)
             .build();
        UserFullNameGeneric temp = producerTemplate.requestBodyAndHeaders(
             "direct://getUserFullname", null, headers, UserFullNameGeneric.class
        );
        UserFullNameDTO result = Optional.ofNullable(temp.getResponse().getFirst()).orElseThrow(UserNotFoundException::new);
        return result;
    }


}
