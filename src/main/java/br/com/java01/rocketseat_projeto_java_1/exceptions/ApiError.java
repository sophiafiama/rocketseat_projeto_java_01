package br.com.java01.rocketseat_projeto_java_1.exceptions;

import lombok.Builder;

@Builder
public record ApiError(String error, String message, Integer status) {

}
