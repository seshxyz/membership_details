package com.thiscompany.membership_details.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Ответ с информацией о пользователе", description = "Ответная информация от API, содержащая данные о пользователе")
public record MembershipInfoResponse(

        @Schema(
            description = "Имя пользователя",
            example = "Человек",
            type = "String"
        )
        @JsonProperty(value = "first_name")
        String firstName,

        @Schema(
            description = "Фамилия пользователя",
            example = "Паук",
            type = "String"
        )
        @JsonProperty(value = "last_name")
        String lastName,

        @Schema(
            description = "Отчество пользователя",
            example = "Супергерой",
            type = "String"
        )
        @JsonProperty(value = "nickname")
        String nickname,

        @Schema(
            description = "Является ли участником группы",
            example = "true",
            type = "String"
        )
        boolean member

) {
}
