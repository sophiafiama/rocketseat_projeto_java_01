package br.com.java01.rocketseat_projeto_java_1.modules.courses.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateCourseDTO {
    @NotEmpty
    private String name;

    @NotEmpty
    private String category;

    private boolean active;
}
