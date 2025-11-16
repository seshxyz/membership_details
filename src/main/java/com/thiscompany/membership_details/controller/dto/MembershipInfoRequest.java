package com.thiscompany.membership_details.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(name = "Тело запроса информации о пользователе", description = "Тело запроса должно содержать идентификатор пользователя и группы")
public record MembershipInfoRequest(

        @Schema(
                description = "Идентификатор пользователя",
                example = "123",
                type = "String",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @JsonProperty(value = "user_id", required = true)
        @NotBlank(message = "user.id.is_empty")
        int userId,

        @Schema(
            description = "Идентификатор группы",
            example = "321",
            type = "int",
            requiredMode = Schema.RequiredMode.REQUIRED
        )
        @JsonProperty(value = "group_id", required = true)
        @NotBlank(message = "group.id.is_empty")
        int groupId

) {
}
