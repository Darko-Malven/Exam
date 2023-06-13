<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Courses</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
</head>
<body>
	<div class="container">
		<h1>Welcome, ${userInSession.name} ${userInSession.lastName}</h1>
		<a href="/logout" class="btn btn-danger">Logout</a>
		<table class="table table-hover">
			<thead>
				<tr>
					<th>Course</th>
					<th>Instructor</th>
					<th>Signups</th>
					<th>Action</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${myCourses}" var="course">
					<tr>
						<td><a href="/courses/${course.id}">${course.nameCourse}</a></td>
						<td>${course.instructor}</td>
						<td>${course.capacity}</td>
						<td>
							<a href="/join/${course.id}" class="btn btn-primary">Add</a>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<a href="/courses/new" class="btn btn-primary">Add a new course</a>
	</div>
</body>
</html>