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
		<h1>${course.nameCourse}</h1>
		<p>Intructor: ${course.instructor}</p>
		<p>Sign ups: ${course.capacity}</p>
		<table class="table table-hover">
			<thead>
				<tr>
					<th>Name</th>
					<th>Sign Up Date</th>
					<th>Action</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${users}" var="user">
					<tr>
						<td>${user.name} ${user.lastName}</td>
						<td>${user.createdAt}</td>
						<td>
							<a href="/remove/${course.id}/${user.id}" class="btn btn-danger">Remove</a>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<a href="/courses/${course.id}/edit" class="btn btn-primary">Edit</a>
		<form action="/delete/${course.id }" method="post">
			<input type="hidden" name="_method" value="DELETE">
			<input type="submit" value="Delete" class="btn btn-danger">
		</form>
	</div>
</body>
</html>