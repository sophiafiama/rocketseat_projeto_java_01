package br.com.java01.rocketseat_projeto_java_1.exceptions;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import java.io.Serial;

/**
 * Exception thrown when a requested resource is not found.
 * This exception extends {@link ApiException} and is used to indicate
 * that a specific resource could not be found.
 *
 * <p>It provides constructors to create an exception instance with a code,
 * description, and optionally a cause.</p>
 *
 * <p>Example usage:</p>
 * <pre>
 * {@code
 * throw new NotFoundException("RESOURCE_NOT_FOUND", "The requested resource was not found.");
 * }
 * </pre>
 *
 * @see ApiException
 */
public class NotFoundException extends ApiException {

    @Serial
    private static final long serialVersionUID = 149728188L;

    /**
    *
    * @param code        the error code representing the specific not found error
    * @param description a description of the error
    */
    public NotFoundException(String code, String description) {
        super(code, description, NOT_FOUND.value());
    }

    /**
    *
    * @param code        the error code representing the specific not found error
    * @param description a description of the error
    * @param cause       the underlying cause of the exception
    */
    public NotFoundException(String code, String description, Throwable cause) {
        super(code, description, NOT_FOUND.value(), cause);
    }
}
