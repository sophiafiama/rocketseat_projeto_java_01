package br.com.java01.rocketseat_projeto_java_1.modules.courses.service;

import br.com.java01.rocketseat_projeto_java_1.modules.courses.exceptions.CourseNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.com.java01.rocketseat_projeto_java_1.modules.courses.dto.CreateCourseDTO;
import br.com.java01.rocketseat_projeto_java_1.modules.courses.model.Course;
import br.com.java01.rocketseat_projeto_java_1.modules.courses.repository.CourseRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CourseServiceImpl implements CourseService {

  @Autowired
  private CourseRepository courseRepository;

  @Override
  public Course create(CreateCourseDTO createCourseDTO) {
    var course = Course.builder()
        .name(createCourseDTO.getName())
        .category(createCourseDTO.getCategory())
        .active(createCourseDTO.isActive())
        .build();

    return courseRepository.save(course);
  }

  @Override
  public Course getById(Long id) {
    Optional<Course> course = courseRepository.findById(id);
    if (course.isEmpty()) {
      throw new CourseNotFoundException();
    }

    return course.get();
  }

  @Override
  public void delete(Long id) {
    Course course = getById(id);
    courseRepository.delete(course);
  }

  public Course toggleStatus(Long id) {
    Course course = getById(id);
    course.setActive(!course.getActive());
    return courseRepository.save(course);
  }

  public List<Course> getAll() {
    return courseRepository.findAll();
  }

}
