package com.thiscompany.membership_details.controller;

import com.thiscompany.membership_details.controller.dto.MembershipInfoResponse;
import com.thiscompany.membership_details.controller.dto.MembershipInfoRequest;
import com.thiscompany.membership_details.service.UserInfoOrchestrator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class MainController {

    private final UserInfoOrchestrator infoOrchestrator;

    @PostMapping(value = "/check-user-membership", consumes = MediaType.APPLICATION_JSON_VALUE)
    public MembershipInfoResponse checkIsUserMemberOfGroup(@RequestBody MembershipInfoRequest request) {
        return infoOrchestrator.getMemberDetails(request);
    }

}
