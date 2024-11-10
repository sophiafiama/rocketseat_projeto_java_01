package br.com.java01.rocketseat_projeto_java_1.modules.courses.exceptions;

import lombok.Builder;

@Builder
public record ApiError(String error, String message, Integer status) {

}
