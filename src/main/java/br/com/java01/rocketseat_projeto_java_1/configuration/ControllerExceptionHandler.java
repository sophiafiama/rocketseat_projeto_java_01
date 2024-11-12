package br.com.java01.rocketseat_projeto_java_1.configuration;

import static java.util.stream.Collectors.joining;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

import br.com.java01.rocketseat_projeto_java_1.exceptions.ApiError;
import br.com.java01.rocketseat_projeto_java_1.exceptions.ApiException;
import jakarta.servlet.http.HttpServletRequest;
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

    /**
     * Handles MethodArgumentNotValidException exceptions and constructs a detailed
     * error response.
     *
     * @param ex      the MethodArgumentNotValidException thrown when validation
     *                fails
     * @param request the HttpServletRequest containing the request details
     * @return a ResponseEntity containing an ApiError with details about the
     *         validation errors
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ApiError> handleValidationExceptions(
            MethodArgumentNotValidException ex, HttpServletRequest request) {
        String requestParams = extractParameters(request);
        String requestUrl = request.getRequestURL().toString();

        Map<String, String> errors = new ConcurrentHashMap<>();
        ex.getBindingResult()
                .getAllErrors()
                .forEach(
                        (error) -> {
                            String fieldName = ((FieldError) error).getField();
                            String errorMessage = error.getDefaultMessage();
                            errors.put(fieldName, errorMessage);
                        });

        String message = errors.entrySet().stream()
                .map(entry -> "Field '%s' error: %s".formatted(entry.getKey(), entry.getValue()))
                .collect(joining(semicolon));

        log.warn("[Validation error] URL: {} Parameters: {}",
                requestUrl, requestParams);

        ApiError apiError = new ApiError("field_valid_error", message, BAD_REQUEST.value());
        return ResponseEntity.status(apiError.status()).body(apiError);
    }

    /**
     * Handler for method argument type mismatches, such as invalid enum constants.
     *
     * @param ex      the exception thrown when method argument type mismatch
     *                occurs.
     * @param request the HttpServletRequest that resulted in the exception
     * @return {@link ResponseEntity} with BAD_REQUEST status indicating an invalid
     *         input.
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiError> handleMethodArgumentTypeMismatchException(
            MethodArgumentTypeMismatchException ex, HttpServletRequest request) {
        String requestParams = extractParameters(request);
        String requestUrl = request.getRequestURL().toString();

        log.warn("[Argument type mismatch] URL: {} Parameters: {}",
                requestUrl, requestParams);

        String message = String.format("Invalid value for path variable '%s': %s",
                ex.getName(), ex.getValue());

        ApiError apiError = new ApiError("invalid_argument_type", message, BAD_REQUEST.value());
        return ResponseEntity.status(apiError.status()).body(apiError);
    }
}
