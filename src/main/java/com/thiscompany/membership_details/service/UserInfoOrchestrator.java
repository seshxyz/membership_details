package com.thiscompany.membership_details.service;

import com.thiscompany.membership_details.controller.dto.MembershipInfoRequest;
import com.thiscompany.membership_details.controller.dto.MembershipInfoResponse;
import com.thiscompany.membership_details.controller.dto.UserFullNameDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserInfoOrchestrator {

    private final UserDetailsService userService;
    private final GroupMembershipChecker membershipChecker;

    public MembershipInfoResponse getMemberDetails(MembershipInfoRequest request) {
        UserFullNameDTO userFullname = userService.getUserFullname(request.userId());
        boolean isMember = membershipChecker.checkUserMembership(request);
        return new MembershipInfoResponse(
             userFullname.firstName(),
             userFullname.lastName(),
             userFullname.nickname(),
             isMember
        );
    }

}
