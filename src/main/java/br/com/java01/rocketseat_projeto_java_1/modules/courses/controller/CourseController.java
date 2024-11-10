package br.com.java01.rocketseat_projeto_java_1.modules.courses.controller;

import br.com.java01.rocketseat_projeto_java_1.modules.courses.dto.CourseFilterDTO;
import br.com.java01.rocketseat_projeto_java_1.modules.courses.dto.CreateCourseDTO;
import br.com.java01.rocketseat_projeto_java_1.modules.courses.model.Course;
import br.com.java01.rocketseat_projeto_java_1.modules.courses.service.CourseService;
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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PatchMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/cursos")
@Tag(name = "Curso", description = "Informações sobre os cursos")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @PostMapping
    @Operation(summary = "Cadastro de cursos", description = "Essa função é responsável por cadastrar um novo curso")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Curso cadastrado com sucesso", content = @Content(schema = @Schema(implementation = Course.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content)
    })
    public ResponseEntity<Course> createCourse(@Valid @RequestBody CreateCourseDTO createCourseDTO) {
        Course createdCourse = courseService.create(createCourseDTO);
        return new ResponseEntity<>(createdCourse, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter curso", description = "Obtém um curso cadastrado, buscando pelo seu ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Curso encontrado", content = @Content(schema = @Schema(implementation = Course.class))),
            @ApiResponse(responseCode = "404", description = "Curso não encontrado", content = @Content)
    })
    public ResponseEntity<?> getCourseById(@PathVariable @Min(1) @Valid Long id) {
        Course course = courseService.getById(id);
        return ResponseEntity.ok(course);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remover curso", description = "Remove um curso cadastrado, identificado pelo seu ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Curso removido", content = @Content),
            @ApiResponse(responseCode = "404", description = "Curso não encontrado", content = @Content)
    })
    public ResponseEntity<?> delete(@PathVariable @Min(1) @Valid Long id) {
        courseService.delete(id);
        return new ResponseEntity<>("", HttpStatus.NO_CONTENT);
    }

    @GetMapping
    @Operation(summary = "Obter todos os cursos", description = "Obtem a lista completa de cursos cadastrados")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de cursos obtida com sucesso", content = @Content(schema = @Schema(implementation = Course.class))),
    })
    public ResponseEntity<?> getAll(@ModelAttribute CourseFilterDTO filter) {
        List<Course> courses = courseService.getAll(filter);
        return ResponseEntity.ok(courses);
    }

    @PatchMapping("/{id}/active")
    @Operation(summary = "Atualizar status do curso", description = "Atualiza o status de um curso cadastrado identificado pelo seu ID, para ativo se estiver inativo, e para inativo se estiver ativo")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Status do curso atualizado", content = @Content(schema = @Schema(implementation = Course.class))),
            @ApiResponse(responseCode = "404", description = "Curso não encontrado", content = @Content)
    })
    public ResponseEntity<?> updateStatus(@PathVariable Long id) {
        Course updatedCourse = courseService.toggleStatus(id);
        return ResponseEntity.ok(updatedCourse);
    }
}
