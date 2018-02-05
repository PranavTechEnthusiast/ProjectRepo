package io.newbieApps.springboot.course;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/*SPRING SERVICE CLASSES ARE SINGLETON*/
@Service	
public class CourseService {
	@Autowired
	private CourseRepository courseRepository;
			
	public List<Course> getAllCourses(String topicId){
		List<Course> courses=new ArrayList<Course>();
		courseRepository.findByTopicId(topicId)
		.forEach(courses::add);
		return courses;
	}

	public Course getCourse(String id) {
		return courseRepository.findOne(id);
	}

	public void addCourse(Course course) {
		courseRepository.save(course);
	}

	public void updateCourse(Course course) {
		courseRepository.save(course);
	}
	
	public void deleteCourse(String courseId){
		courseRepository.delete(courseId);
	}
}
