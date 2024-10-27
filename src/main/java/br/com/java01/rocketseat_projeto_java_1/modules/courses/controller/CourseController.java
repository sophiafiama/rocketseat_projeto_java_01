package br.com.java01.rocketseat_projeto_java_1.modules.courses.controller;

import br.com.java01.rocketseat_projeto_java_1.modules.courses.exceptions.CourseNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import br.com.java01.rocketseat_projeto_java_1.modules.courses.dto.CreateCourseDTO;
import br.com.java01.rocketseat_projeto_java_1.modules.courses.model.Course;
import br.com.java01.rocketseat_projeto_java_1.modules.courses.service.CourseService;

@RestController
@RequestMapping("/cursos")
@Tag(name = "Curso", description = "Informações dos cursos")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @PostMapping
    @Operation(summary = "Cadastro de cursos", description = "Essa função é responsável por cadastrar um novo curso")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Curso cadastrado com sucesso", content = @Content(
            schema = @Schema(implementation = Course.class))
        ),
        @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content)
    })
    public ResponseEntity<Course> createCourse(@Valid @RequestBody CreateCourseDTO createCourseDTO) {
        Course createdCourse = courseService.createCourse(createCourseDTO);
        return new ResponseEntity<>(createdCourse, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter curso", description = "Obtem um curso cadastrado, buscando pelo seu ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Curso encontrado", content = @Content(
            schema = @Schema(implementation = Course.class))
        ),
        @ApiResponse(responseCode = "404", description = "Curso não encontrado", content = @Content)
    })
    public ResponseEntity<?> getCourseById(@PathVariable @Min(1) @Valid Long id) {
        try {
            Course course = courseService.getCourseById(id);
            return ResponseEntity.ok(course);
        } catch (CourseNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
