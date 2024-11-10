package br.com.java01.rocketseat_projeto_java_1.modules.courses.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;

@Builder
public record CreateCourseDTO(
        @JsonProperty("name")
        @NotEmpty(message = "O nome do curso é obrigatório")
        String name,
        @JsonProperty("category")
        @NotEmpty(message = "A categoria do curso é obrigatória")
        String category
) {

}
