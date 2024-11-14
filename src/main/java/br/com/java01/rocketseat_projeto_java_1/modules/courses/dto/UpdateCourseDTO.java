package br.com.java01.rocketseat_projeto_java_1.modules.courses.dto;

import jakarta.validation.constraints.AssertTrue;
import lombok.Builder;

@Builder
public record UpdateCourseDTO(String name, String category) {

    @AssertTrue(message = "Either name or category must be provided")
    public boolean isValid() {
        return (name != null && !name.isEmpty()) || (category != null && !category.isEmpty());
    }
}
