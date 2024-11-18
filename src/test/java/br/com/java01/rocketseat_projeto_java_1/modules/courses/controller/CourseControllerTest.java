package br.com.java01.rocketseat_projeto_java_1.modules.courses.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import br.com.java01.rocketseat_projeto_java_1.modules.courses.dto.CreateCourseDTO;
import br.com.java01.rocketseat_projeto_java_1.modules.courses.dto.UpdateCourseDTO;
import br.com.java01.rocketseat_projeto_java_1.modules.courses.model.Course;
import br.com.java01.rocketseat_projeto_java_1.modules.courses.service.CourseService;
import br.com.java01.rocketseat_projeto_java_1.modules.courses.repository.CourseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

class CourseControllerTest {

    @InjectMocks
    private CourseController courseController;

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private CourseService courseService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should create a course successfully")
    void shouldCreateCourse() {
        CreateCourseDTO createCourseDTO = CreateCourseDTO.builder()
                .name("Curso Teste")
                .category("Categoria do curso teste")
                .build();

        Course createdCourse = Course.builder()
                .id(1L)
                .name("Curso Teste")
                .category("Categoria do curso teste")
                .build();

        when(courseService.create(createCourseDTO)).thenReturn(createdCourse);

        ResponseEntity<Course> response = courseController.createCourse(createCourseDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(createdCourse, response.getBody());
        verify(courseService, times(1)).create(createCourseDTO);
    }

    @Test
    @DisplayName("Should get a course by its ID")
    void shouldGetCourseById() {
        Long courseId = 1L;
        Course course = Course.builder()
                .id(courseId)
                .name("Curso Teste")
                .category("Categoria do curso teste")
                .build();

        when(courseService.getById(courseId)).thenReturn(course);

        ResponseEntity<?> response = courseController.getCourseById(courseId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(course, response.getBody());
        verify(courseService, times(1)).getById(courseId);
    }

    @Test
    @DisplayName("Should delete a course successfully")
    void shouldDeleteCourse() {
        Long courseId = 1L;

        ResponseEntity<?> response = courseController.delete(courseId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(courseService, times(1)).delete(courseId);
    }

    @Test
    @DisplayName("Should get all courses successfully")
    void shouldGetAllCourses() {
        List<Course> courses = List.of(
                Course.builder().id(1L).name("Curso 1").category("Categoria 1").build(),
                Course.builder().id(2L).name("Curso 2").category("Categoria 2").build());

        when(courseService.getAll(any())).thenReturn(courses);

        ResponseEntity<?> response = courseController.getAll(null);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(courses, response.getBody());
        verify(courseService, times(1)).getAll(any());
    }

    @Test
    @DisplayName("Should update the status of a course successfully")
    void shouldUpdateCourseStatus() {
        Long courseId = 1L;
        Course updatedCourse = Course.builder()
                .id(courseId)
                .name("Curso Teste")
                .category("Categoria do curso teste")
                .build();

        when(courseService.toggleStatus(courseId)).thenReturn(updatedCourse);

        ResponseEntity<?> response = courseController.updateStatus(courseId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedCourse, response.getBody());
        verify(courseService, times(1)).toggleStatus(courseId);
    }

    @Test
    @DisplayName("Should update a course successfully")
    void shouldUpdateCourse() {
        Long courseId = 1L;
        UpdateCourseDTO updateCourseDTO = UpdateCourseDTO.builder()
                .name("Curso Atualizado")
                .category("Categoria atualizada")
                .build();

        Course updatedCourse = Course.builder()
                .id(courseId)
                .name("Curso Atualizado")
                .category("Categoria atualizada")
                .build();

        when(courseService.update(courseId, updateCourseDTO)).thenReturn(updatedCourse);

        ResponseEntity<?> response = courseController.updateCourse(courseId, updateCourseDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedCourse, response.getBody());
        verify(courseService, times(1)).update(courseId, updateCourseDTO);
    }
}