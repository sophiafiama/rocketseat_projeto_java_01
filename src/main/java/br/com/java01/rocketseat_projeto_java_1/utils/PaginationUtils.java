package br.com.java01.rocketseat_projeto_java_1.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Sort;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PaginationUtils {
    private static final String SORT_BY_FIELD = "id";

    public static Sort sortBy(String sortBy, Sort.Direction sortOrder) {
        if (sortBy == null || sortBy.isEmpty()) {
            return Sort.by(sortOrderOrDefault(sortOrder), SORT_BY_FIELD);
        }

        // Quando filtra por sortBy, inclui ordenação por id (unique) para garantir
        // ordenação unica
        return Sort.by(sortOrderOrDefault(sortOrder), sortBy, SORT_BY_FIELD);
    }

    private static Sort.Direction sortOrderOrDefault(Sort.Direction sortOrder) {
        return (sortOrder != null) ? sortOrder : Sort.Direction.ASC;
    }
}
