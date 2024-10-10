package br.com.java01.rocketseat_projeto_java_1.modules.courses.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.java01.rocketseat_projeto_java_1.modules.courses.model.Course;

public interface CourseRepository extends JpaRepository<Course, Long> {
}
