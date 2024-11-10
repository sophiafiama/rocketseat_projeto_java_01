package br.com.java01.rocketseat_projeto_java_1.modules.courses.service;

import br.com.java01.rocketseat_projeto_java_1.modules.courses.dto.CreateCourseDTO;
import br.com.java01.rocketseat_projeto_java_1.modules.courses.model.Course;

import java.util.List;

public interface CourseService {
  Course create(CreateCourseDTO createCourseDTO);

  Course getById(Long id);

  void delete(Long id);

  Course toggleStatus(Long id);

  List<Course> getAll();
}
