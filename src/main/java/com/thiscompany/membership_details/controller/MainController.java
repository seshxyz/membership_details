package com.thiscompany.membership_details.controller;

import com.thiscompany.membership_details.controller.dto.MembershipInfoRequest;
import com.thiscompany.membership_details.controller.dto.MembershipInfoResponse;
import com.thiscompany.membership_details.service.UserInfoOrchestrator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.RejectedExecutionException;



@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class MainController {

    private final UserInfoOrchestrator infoOrchestrator;

    @PostMapping(value = "/check-user-membership", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MembershipInfoResponse> checkIsUserMemberOfGroup(@RequestHeader(name = "vk_service_token", required = true) String token, @RequestBody MembershipInfoRequest request) throws RejectedExecutionException {
        return ResponseEntity.ok(infoOrchestrator.assembleMemberDetails(request));
    }

}
