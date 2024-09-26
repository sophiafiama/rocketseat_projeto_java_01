package br.com.java01.rocketseat_projeto_java_1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import br.com.java01.rocketseat_projeto_java_1.dto.CourseDTO;
import br.com.java01.rocketseat_projeto_java_1.model.Course;
import br.com.java01.rocketseat_projeto_java_1.service.CourseService;

@RestController
@RequestMapping("/cursos")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @PostMapping
    public ResponseEntity<Course> createCourse(@RequestBody CourseDTO courseDTO) {
        Course createdCourse = courseService.createCourse(courseDTO);
        return new ResponseEntity<>(createdCourse, HttpStatus.CREATED);
    }
}

