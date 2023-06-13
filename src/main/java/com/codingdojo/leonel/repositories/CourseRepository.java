package com.codingdojo.leonel.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.codingdojo.leonel.models.Course;
import com.codingdojo.leonel.models.User;

	@Repository
	public interface CourseRepository extends CrudRepository<Course,Long> {
		
		List<Course> findAll();
	}
