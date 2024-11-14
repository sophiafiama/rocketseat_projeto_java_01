package br.com.java01.rocketseat_projeto_java_1.modules.courses.service;

import static br.com.java01.rocketseat_projeto_java_1.utils.PaginationUtils.sortBy;

import br.com.java01.rocketseat_projeto_java_1.modules.courses.dto.CourseFilterDTO;
import br.com.java01.rocketseat_projeto_java_1.modules.courses.dto.CreateCourseDTO;
import br.com.java01.rocketseat_projeto_java_1.modules.courses.exceptions.CourseNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import br.com.java01.rocketseat_projeto_java_1.modules.courses.model.Course;
import br.com.java01.rocketseat_projeto_java_1.modules.courses.repository.CourseRepository;
import org.springframework.data.domain.Sort;

import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {

  @Autowired
  private CourseRepository courseRepository;

  @Override
  public Course create(CreateCourseDTO createCourseDTO) {
    var course = Course.builder()
        .name(createCourseDTO.name())
        .category(createCourseDTO.category())
        .active(true)
        .build();

    return courseRepository.save(course);
  }

  @Override
  public Course getById(Long id) {
    Course course = courseRepository.findById(id)
        .orElseThrow(() -> new CourseNotFoundException(id));

    return course;
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

  public List<Course> getAll(CourseFilterDTO filter) {
    Sort sort = sortBy(filter.sortBy(), filter.sortOrder());

    Course courseExample = new Course();
    courseExample.setName(filter.name());
    courseExample.setCategory(filter.category());

    ExampleMatcher matcher = ExampleMatcher.matchingAll()
        .withIgnoreNullValues()
        .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING); // busca parcial (contém)

    Example<Course> example = Example.of(courseExample, matcher);

    return courseRepository.findAll(example, sort);
  }

}
