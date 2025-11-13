package com.thiscompany.membership_details.camel.router;

import com.thiscompany.membership_details.camel.processor.ExternalApiResponseProcessor;
import com.thiscompany.membership_details.utils.CamelRouteDirects;
import com.thiscompany.membership_details.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.processor.ThrottlerRejectedExecutionException;
import org.springframework.stereotype.Component;

import java.util.concurrent.RejectedExecutionException;

import static com.thiscompany.membership_details.utils.ExternalApiConst.Path;
import static com.thiscompany.membership_details.utils.ExternalApiConst.Url;

@Component
@RequiredArgsConstructor
public class ApplicationRouter extends RouteBuilder {

    private final ExternalApiResponseProcessor processor;

    @Override
    public void configure() throws RejectedExecutionException {
        onException(ThrottlerRejectedExecutionException.class)
             .handled(false);
        callGetUserFullname();
        callIsUserMember();
    }

    private void callGetUserFullname() {
        from(CamelRouteDirects.GET_USER_DETAILS)
            .setHeader("Content-Type", constant("application/json"))
            .setHeader(Utils.Const.AUTH_HEADER, simple("Bearer ${header.token}"))
            .removeHeader("token")
            .throttle(2)
            .totalRequestsMode()
            .timePeriodMillis(1000)
            .rejectExecution(true)
            .toD(Url.VK_BASE_URL + Path.GET_USER_DETAILS_PATH)
            .process(processor);
    }

    private void callIsUserMember() {
        from(CamelRouteDirects.GET_IS_MEMBER)
            .setHeader("Content-Type", constant("application/json"))
            .setHeader(Utils.Const.AUTH_HEADER, simple("Bearer ${header.token}"))
            .removeHeader("token")
            .throttle(2)
            .totalRequestsMode()
            .timePeriodMillis(1000)
            .rejectExecution(true)
            .toD(Url.VK_BASE_URL + Path.GET_IS_MEMBER)
            .process(processor);
    }

}
