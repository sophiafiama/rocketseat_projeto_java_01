package br.com.java01.rocketseat_projeto_java_1.configuration;

import static java.util.stream.Collectors.joining;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

import br.com.java01.rocketseat_projeto_java_1.modules.courses.exceptions.ApiError;
import br.com.java01.rocketseat_projeto_java_1.modules.courses.exceptions.ApiException;
import jakarta.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
@Slf4j
public class ControllerExceptionHandler {
    private static final String semicolon = "; ";


    private String extractParameters(HttpServletRequest request) {
        Map<String, String[]> paramMap = request.getParameterMap();
        StringBuilder params = new StringBuilder();
        paramMap.forEach((key, value) -> {
            params.append(key).append('=').append(String.join(",", value)).append(semicolon);
        });
        return params.toString();
    }

    /**
     * Handler for external API exceptions.
     *
     * @param e       the exception thrown during a request to external API.
     * @param request the HttpServletRequest that resulted in the exception
     * @return {@link ResponseEntity} with status code and description provided for
     *         the handled
     *         exception.
     */
    @ExceptionHandler(ApiException.class)
    protected ResponseEntity<ApiError> handleApiException(ApiException e, HttpServletRequest request) {
        String requestParams = extractParameters(request);
        String requestUrl = request.getRequestURL().toString();

        Integer statusCode = e.getStatusCode();
        boolean expected = HttpStatus.INTERNAL_SERVER_ERROR.value() > statusCode;
        if (expected) {
            log.warn("[Internal Api warn] URL: {} Parameters: {} Status Code: {}",
                    requestUrl, requestParams, statusCode, e);
        } else {
            log.error("[Internal Api error] URL: {} Parameters: {} Status Code: {}",
                    requestUrl, requestParams, statusCode, e);
        }

        ApiError apiError = new ApiError(e.getCode(), e.getDescription(), statusCode);
        return ResponseEntity.status(apiError.status()).body(apiError);
    }


}
