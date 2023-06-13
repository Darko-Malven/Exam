package com.codingdojo.leonel.controllers;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.codingdojo.leonel.models.Course;
import com.codingdojo.leonel.models.User;
import com.codingdojo.leonel.services.AppService;

@Controller
public class MainController {
	@Autowired
	private AppService service;
	
	@GetMapping("/")
	public String index(@ModelAttribute("newUser") User newUser) {
		
		return "index.jsp";
	}
	@PostMapping("/register")
	public String newUser(@Valid @ModelAttribute("newUser") User newUser,
						  BindingResult result,
						  HttpSession session) {
		 service.register(newUser, result);
		if(result.hasErrors()) {
			return "index.jsp";
		}
		else {
			session.setAttribute("userInSession", newUser);
			return "redirect:/courses";	
		}
	}
	@PostMapping("/login")
	public String login(@RequestParam ("email") String email,
						@RequestParam ("password") String password,
						RedirectAttributes redirectAttributes,
						HttpSession session) {
		//enviar email y contraseña para q el servicio verifique que este correcto
		User userLogin = service.login(email,password);
		if(userLogin==null) {
			redirectAttributes.addFlashAttribute("error_login", "The email/password is incorrect.");
			return "redirect:/";
		}
		else {
			session.setAttribute("userInSession", userLogin);
			return "redirect:/courses";  
		}
		 
	}
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.removeAttribute("userInSession");
		return "redirect:/";
	}
	
	@GetMapping("/courses")
	public String coursesMain(HttpSession session, Model model) {
		User userInSession= (User)session.getAttribute("userInSession");
		if(userInSession==null) {
			return "redirect:/";
		}
		model.addAttribute("myCourses", service.findCourses());
		return "courses.jsp";
	}
	@GetMapping("/courses/new")
	public String newCourse(HttpSession session,@ModelAttribute("newCourse") Course newCourse) {
		User userInSession= (User)session.getAttribute("userInSession");
		if(userInSession==null) {
			return "redirect:/";
		}
		return "new.jsp";
	}
	@PostMapping("/create")
	public String createCourse(HttpSession session,
							   @Valid @ModelAttribute("newCourse") Course newCourse,
							   BindingResult result) {
		User userInSession= (User)session.getAttribute("userInSession");
		if(userInSession==null) {
			return "redirect:/";
		}
		if(result.hasErrors()) {
			return "new.jsp";
		}
		else {
			Course course=service.saveCourse(newCourse);
			User user= service.findUser(userInSession.getId());
			user.getCourseJoined().add(course);
			service.saveUser(user);
			return "redirect:/courses";
		}
	}
	@GetMapping("/join/{courseId}")
	public String join(@PathVariable("courseId") Long courseId,
					   HttpSession session) {
		User userInSession = (User)session.getAttribute("userInSession");
		if(userInSession == null) {
			return "redirect:/";
		}
		service.joinCourse(userInSession.getId(), courseId);
		return "redirect:/courses";
	}
	@GetMapping("/leave/{courseId}")
	public String leave(@PathVariable("courseId") Long courseId,
			   			HttpSession session) {
		User userInSession = (User)session.getAttribute("userInSession");
		
		if(userInSession == null) {
			return "redirect:/";
		}
		service.leaveCourse(userInSession.getId(), courseId);
		return "redirect:/courses";
	}
	
	
	@GetMapping("/remove/{courseId}/{userId}")
	public String removeUser(@PathVariable("courseId") Long courseId,
							@PathVariable("userId") Long userId,
			   			     HttpSession session) {
		User userInSession = (User)session.getAttribute("userInSession");
		
		if(userInSession == null) {
			return "redirect:/";
		}
		User userRemove = service.findUser(userId);
		service.removeUser(userRemove.getId(), courseId);
		
		return "redirect:/courses/"+courseId;
	}
	
	
	@GetMapping("/courses/{courseId}/edit")
	public String edit(@PathVariable("courseId") Long id,
					   @ModelAttribute("courseEdit") Course course,
					   HttpSession session,
					   Model model) {
		User userInSession = (User)session.getAttribute("userInSession");
		
		if(userInSession == null) {
			return "redirect:/";
		}	
		Course courseEdit = service.findCourse(id);
		model.addAttribute("course", courseEdit);
		return "edit.jsp";
		
	}
	@PutMapping("/update")
	public String update(@Valid @ModelAttribute("courseEdit") Course courseEdit,
						 BindingResult result,
						 HttpSession session) {
		User userInSession = (User)session.getAttribute("userInSession");
		
		if(userInSession == null) {
			return "redirect:/";
		}	
		if(result.hasErrors()) {
			return "edit.jsp";
		} 
		else {
			Course thisCourse = service.findCourse(courseEdit.getId());
			List <User> usersJoinedInProject = thisCourse.getUserJoined(); //Usuarios que antes se habían unido
			courseEdit.setUserJoined(usersJoinedInProject); //Agregando al project del formulario
			service.saveCourse(courseEdit);
			return "redirect:/courses";
		}
	}
	@GetMapping("/courses/{id}")
	public String showCourse(HttpSession session,
							 Model model,
							 @PathVariable ("id")Long id) {
		User userInSession = (User)session.getAttribute("userInSession");
		if(userInSession == null) {
			return "redirect:/";
		}

		Course course = service.findCourse(id);
		List<User> userList = course.getUserJoined();
		model.addAttribute("users",userList);
		model.addAttribute("course", service.findCourse(id));
		return "show.jsp";
	}
	@DeleteMapping("/delete/{id}")
	public String deleteCourse(@PathVariable("id")Long id,
								HttpSession session) {
		User userInMethod = (User)session.getAttribute("userInSession");
		
		if(userInMethod == null) {
			return "redirect:/";
		}
		Course courseId = service.findCourse(id);
		service.deleteCourse(courseId.getId());
		return "redirect:/courses";
	}
}
