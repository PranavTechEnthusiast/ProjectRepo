package io.newbieApps.springboot.course;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import io.newbieApps.springboot.topic.Topic;

public interface CourseRepository extends CrudRepository<Course, String> {
	/*For adding custom methods in spring boot one does not require to write implementation of methods.
	 * Just write method declaration in Repository interface  following standard Spring-boot method signature
	 * and spring-boot will implement it for you. The Signature syntax follow-
	 * access_sp return_type find+"Property_Name"+"SubProp_Name"(Parameters)*/
	public List<Course> findByTopicId (String topicId);
}
