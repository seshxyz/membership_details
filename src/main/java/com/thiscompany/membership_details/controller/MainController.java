package com.thiscompany.membership_details.controller;

import com.thiscompany.membership_details.controller.dto.MembershipInfoRequest;
import com.thiscompany.membership_details.controller.dto.MembershipInfoResponse;
import com.thiscompany.membership_details.service.UserInfoOrchestrator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.RejectedExecutionException;



@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Tag(name = "Информация о пользователе", description = "Контроллер для получения данных о пользователе")
public class MainController {

    private final UserInfoOrchestrator infoOrchestrator;

    @Operation(
        summary = "Получить информацию о пользователе",
        description = "Эндпоинт позволяет направить идентификаторы группы и пользователя, чтобы проверить состоит ли пользователь в группе Вконтакте через VK API"
    )
    @ApiResponses( value = {
        @ApiResponse(
            responseCode = "200",
            description = "пользователь найден",
            content = @Content(
                schema = @Schema(implementation = MembershipInfoResponse.class)
            )
        ),
        @ApiResponse(responseCode = "401", description = "Не авторизован",
            content = @Content(schema = @Schema(implementation = ProblemDetail.class))
        ),@ApiResponse(responseCode = "403", description = "Доступ запрещён",
            content = @Content(schema = @Schema(implementation = ProblemDetail.class))
        ),@ApiResponse(responseCode = "404", description = "Пользователь не найден",
            content = @Content(schema = @Schema(implementation = ProblemDetail.class))
        ),@ApiResponse(responseCode = "400", description = "Некорректный запрос",
            content = @Content(schema = @Schema(implementation = ProblemDetail.class))
        )
    })
    @Parameters({
        @Parameter(
        name = "vk_service_token",
        description = "Сервисный ключ доступа к API Вконтакте",
        in = ParameterIn.HEADER,
        example = "jj123hueh12u3d1u22u124n1",
        required = true)
    })
    @PostMapping(value = "/check-user-membership")
    public ResponseEntity<MembershipInfoResponse> checkIsUserMemberOfGroup(@RequestBody MembershipInfoRequest request) throws RejectedExecutionException {
        return ResponseEntity.ok(infoOrchestrator.assembleMemberDetails(request));
    }

}
