package com.codingdojo.leonel.services;

import java.util.List;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.codingdojo.leonel.models.Course;
import com.codingdojo.leonel.models.User;
import com.codingdojo.leonel.repositories.CourseRepository;
import com.codingdojo.leonel.repositories.UserRepository;

@Service
public class AppService {
	@Autowired 
	UserRepository userRepo;
	@Autowired 
	CourseRepository courseRepo;
	
	public Course saveCourse(Course course) {
		 return courseRepo.save(course);
	 }
	 public User findUser(Long id) {
		 return userRepo.findById(id).orElse(null);
	 }
	 public User saveUser(User user) {
		 return userRepo.save(user);
	 }
	 public List<User> findUsers(){
		 return userRepo.findAll();
	 }

	public User register(User newUser, BindingResult result) {
		 String email = newUser.getEmail();
		 User isUser = userRepo.findByEmail(email);
		 if(isUser!=null) {
			 result.rejectValue(email, "Unique","The email is already in use.");
		 }
		 String password = newUser.getPassword();
		 String confirmPassword = newUser.getConfirm();
		 if(!password.equals(confirmPassword)){
			 result.rejectValue("confirm", "Matches","The passwords don't match");
		 }
		 
		 if(result.hasErrors()) {
			 return null;
		 }
		 else {
			 String encriptation = BCrypt.hashpw(password, BCrypt.gensalt());
			 newUser.setPassword(encriptation);
			 return userRepo.save(newUser);
		 }
		 
	 }
	public User login(String email, String password) {
		 User isUser =  userRepo.findByEmail(email);
		 if(isUser == null) {
			 return null;
		 }
		 if(BCrypt.checkpw(password, isUser.getPassword())) {
			 return isUser;
		 }
		 else {
			 return null;
		 }
	 }
	public Course findCourse(Long id) {
		return courseRepo.findById(id).orElse(null);
	}
	
	
	public void joinCourse(Long userId, Long courseId) {
		User myUser = findUser(userId);
		Course myCourse = findCourse(courseId); 
		
		myCourse.getUserJoined().add(myUser);
		courseRepo.save(myCourse);
	}
	
	
	public void leaveCourse(Long userId, Long courseId) {
		User myUser = findUser(userId); 
		Course myCourse = findCourse(courseId); 
		
		myCourse.getUserJoined().remove(myUser);
		courseRepo.save(myCourse);
	}
	
	public void removeUser(Long userId,Long courseId) {
		User users = findUser(userId);
		Course myCourse = findCourse(courseId);
		users.getCourseJoined().remove(myCourse);
		myCourse.getUserJoined().remove(users);
		userRepo.save(users);
		courseRepo.save(myCourse);
	}
	
	public List<Course> findCourses(){
		return courseRepo.findAll();
	}
	
	public void deleteCourse(Long id) {
		courseRepo.deleteById(id);
	}
	
}
