package br.com.java01.rocketseat_projeto_java_1.exceptions;

import java.io.Serial;
import lombok.Getter;

/**
 * Exception containing relevant information for API errors.
 */
@Getter
public class ApiException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 219337028L;

    private final String code;
    private final String description;
    private final Integer statusCode;

    /**
     * Creates a new instance, with provided fields.
     *
     * @param code        API error code.
     * @param description API error description.
     * @param statusCode  API error HTTP Status code.
     */
    public ApiException(String code, String description, Integer statusCode) {
        super(description);
        this.code = code;
        this.description = description;
        this.statusCode = statusCode;
    }

    /**
     * Creates a new instance, with provided fields.
     *
     * @param code        API error code.
     * @param description API error description.
     * @param statusCode  API error HTTP Status code.
     * @param cause       API error cause.
     */
    public ApiException(String code, String description, Integer statusCode, Throwable cause) {
        super(description, cause);
        this.code = code;
        this.description = description;
        this.statusCode = statusCode;
    }

}
