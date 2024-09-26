package br.com.java01.rocketseat_projeto_java_1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.java01.rocketseat_projeto_java_1.model.Course;

public interface CourseRepository extends JpaRepository<Course, Long> {

}
