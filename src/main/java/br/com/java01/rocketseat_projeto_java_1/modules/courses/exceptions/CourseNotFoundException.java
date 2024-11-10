package br.com.java01.rocketseat_projeto_java_1.modules.courses.exceptions;

import br.com.java01.rocketseat_projeto_java_1.exceptions.NotFoundException;

import java.io.Serial;

/**
 * Exception thrown when a course with the specified ID is not found.
 *
 */
public class CourseNotFoundException extends NotFoundException {
    @Serial
    private static final long serialVersionUID = 321422596L;
    private static final String CODE = "Not found";
    private static final String DESCRIPTION = "Curso com id %s não encontrado";

    /**
     *
     * @param courseId the ID of the course that was not found
     */
    public CourseNotFoundException(Long courseId) {
        super(CODE, String.format(DESCRIPTION, courseId));
    }
}
