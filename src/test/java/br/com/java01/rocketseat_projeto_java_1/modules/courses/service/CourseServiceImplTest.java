package br.com.java01.rocketseat_projeto_java_1.modules.courses.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import br.com.java01.rocketseat_projeto_java_1.modules.courses.dto.CreateCourseDTO;
import br.com.java01.rocketseat_projeto_java_1.modules.courses.dto.UpdateCourseDTO;
import br.com.java01.rocketseat_projeto_java_1.modules.courses.exceptions.CourseNotFoundException;
import br.com.java01.rocketseat_projeto_java_1.modules.courses.model.Course;
import br.com.java01.rocketseat_projeto_java_1.modules.courses.repository.CourseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Optional;

class CourseServiceImplTest {

    @InjectMocks
    private CourseServiceImpl courseService;

    @Mock
    private CourseRepository courseRepository;

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
                .category("Categoria Teste")
                .active(true)
                .build();

        when(courseRepository.save(any(Course.class))).thenReturn(createdCourse);

        Course result = courseService.create(createCourseDTO);

        assertEquals(createdCourse, result);
        verify(courseRepository, times(1)).save(any(Course.class));
    }

    @Test
    @DisplayName("Should get a course by ID successfully")
    void shouldGetCourseById() {
        Long courseId = 1L;
        Course course = Course.builder()
                .id(courseId)
                .name("Curso Teste")
                .category("Categoria Teste")
                .active(true)
                .build();

        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));

        Course result = courseService.getById(courseId);

        assertEquals(course, result);
        verify(courseRepository, times(1)).findById(courseId);
    }

    @Test
    @DisplayName("Should delete a course successfully")
    void shouldDeleteCourse() {
        Long courseId = 1L;
        Course course = Course.builder()
                .id(courseId)
                .name("Curso Teste")
                .category("Categoria Teste")
                .active(true)
                .build();

        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));

        courseService.delete(courseId);

        verify(courseRepository, times(1)).delete(course);
    }

    @Test
    @DisplayName("Should toggle the status of a course to active successfully")
    void shouldToggleCourseToActiveStatus() {
        Long courseId = 1L;
        Course course = Course.builder()
                .id(courseId)
                .name("Curso Teste")
                .category("Categoria Teste")
                .active(true)
                .build();

        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));
        when(courseRepository.save(course)).thenReturn(course);

        Course result = courseService.toggleStatus(courseId);

        assertEquals(false, result.getActive()); // Estado deve ser alterado para inativo
        verify(courseRepository, times(1)).findById(courseId);
        verify(courseRepository, times(1)).save(course);
    }

    @Test
    @DisplayName("Should toggle the status of a course to inactive successfully")
    void shouldToggleCourseToInactiveStatus() {
        Long courseId = 1L;
        Course course = Course.builder()
                .id(courseId)
                .name("Curso Teste")
                .category("Categoria Teste")
                .active(false)
                .build();

        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));
        when(courseRepository.save(course)).thenReturn(course);

        Course result = courseService.toggleStatus(courseId);

        assertEquals(true, result.getActive()); // Estado deve ser alterado para inativo
        verify(courseRepository, times(1)).findById(courseId);
        verify(courseRepository, times(1)).save(course);
    }

    @Test
    @DisplayName("Should update a course successfully")
    void shouldUpdateCourse() {
        Long courseId = 1L;
        UpdateCourseDTO updateCourseDTO = UpdateCourseDTO.builder()
                .name("Curso Atualizado")
                .category("Categoria Atualizada")
                .build();

        Course existingCourse = Course.builder()
                .id(courseId)
                .name("Curso Antigo")
                .category("Categoria Antiga")
                .active(true)
                .updatedAt(LocalDateTime.now().minusDays(1))
                .build();

        Course updatedCourse = Course.builder()
                .id(courseId)
                .name("Curso Atualizado")
                .category("Categoria Atualizada")
                .active(true)
                .updatedAt(LocalDateTime.now())
                .build();

        when(courseRepository.findById(courseId)).thenReturn(Optional.of(existingCourse));
        when(courseRepository.save(existingCourse)).thenReturn(updatedCourse);

        Course result = courseService.update(courseId, updateCourseDTO);

        assertEquals(updatedCourse.getName(), result.getName());
        assertEquals(updatedCourse.getCategory(), result.getCategory());
        verify(courseRepository, times(1)).findById(courseId);
        verify(courseRepository, times(1)).save(existingCourse);
    }

    @Test
    @DisplayName("Should throw exception when updating non-existent course")
    void shouldThrowExceptionWhenUpdatingNonExistentCourse() {
        Long courseId = 1L;
        UpdateCourseDTO updateCourseDTO = UpdateCourseDTO.builder()
                .name("Curso Atualizado")
                .category("Categoria Atualizada")
                .build();

        when(courseRepository.findById(courseId)).thenReturn(Optional.empty());

        assertThrows(CourseNotFoundException.class, () -> courseService.update(courseId, updateCourseDTO));
        verify(courseRepository, times(1)).findById(courseId);
    }

    @Test
    @DisplayName("Should throw exception when course not found")
    void shouldThrowExceptionWhenCourseNotFound() {
        Long courseId = 1L;

        when(courseRepository.findById(courseId)).thenReturn(Optional.empty());

        assertThrows(CourseNotFoundException.class, () -> courseService.getById(courseId));
        verify(courseRepository, times(1)).findById(courseId);
    }

}