package br.com.java01.rocketseat_projeto_java_1.modules.courses.dto;

import org.springframework.data.domain.Sort;
import lombok.Builder;

@Builder
public record CourseFilterDTO(
        String name,
        String category,
        String sortBy,
        Sort.Direction sortOrder) {

}
