package com.thiscompany.membership_details.service;

import com.thiscompany.membership_details.controller.dto.MembershipInfoRequest;
import com.thiscompany.membership_details.controller.dto.MembershipInfoResponse;
import com.thiscompany.membership_details.controller.dto.UserDetailsDTO;
import com.thiscompany.membership_details.service.group.GroupMembershipChecker;
import com.thiscompany.membership_details.service.user.UserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserInfoOrchestrator {

    private final UserDetailsService userService;
    private final GroupMembershipChecker membershipChecker;

    public MembershipInfoResponse assembleMemberDetails(MembershipInfoRequest request) {
        UserDetailsDTO userFullname = userService.getUserDetails(request.userId());
        boolean isMember = membershipChecker.checkUserMembership(request.userId(), request.groupId());
        return new MembershipInfoResponse(
             userFullname.firstName(),
             userFullname.lastName(),
             userFullname.nickname(),
             isMember
        );
    }

}
