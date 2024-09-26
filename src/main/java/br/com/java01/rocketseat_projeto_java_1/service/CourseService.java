package br.com.java01.rocketseat_projeto_java_1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.com.java01.rocketseat_projeto_java_1.dto.CourseDTO;
import br.com.java01.rocketseat_projeto_java_1.model.Course;
import br.com.java01.rocketseat_projeto_java_1.repository.CourseRepository;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    public Course createCourse(CourseDTO courseDTO) {
        Course course = new Course();
        course.setName(courseDTO.getName());
        course.setCategory(courseDTO.getCategory());
        return courseRepository.save(course);
    }
}

