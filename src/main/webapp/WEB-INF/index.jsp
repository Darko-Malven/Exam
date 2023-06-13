<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>new User</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
</head>
<body>
	<div class="container">
		<div class="row"> 
			<div class="col-6">
				<h2>Sing in</h2>
				<form:form action="/register" method="post" modelAttribute="newUser">
					<div>
						<form:label path="name">First Name</form:label>
						<form:input path="name" class="form-control"/>
						<form:errors path="name" class="text-danger"/>
					</div>
					<div>
						<form:label path="lastName">Last Name</form:label>
						<form:input path="lastName" class="form-control"/>
						<form:errors path="lastName" class="text-danger"/>
					</div>
					<div>
						<form:label path="email">Email</form:label>
						<form:input path="email" class="form-control"/>
						<form:errors path="email" class="text-danger"/>
					</div>
					<div>
						<form:label path="password">Password</form:label>
						<form:password path="password" class="form-control"/>
						<form:errors path="password" class="text-danger"/>
					</div>
					<div>
						<form:label path="confirm">Confirm password</form:label>
						<form:password path="confirm" class="form-control"/>
						<form:errors path="confirm" class="text-danger"/>
					</div>
					<input type="submit" class="btn btn-primary  mt-3" value="Create account">
				</form:form>
			</div>
			<div class="col-6">
				<h2>Log in</h2>
				<p class="text-danger">${error_login}</p>
					<form action="/login" method="post">
						<div>
							<label>Email</label>
							<input type="text" class="form-control" name="email"/>
						</div>
						<div>
							<label>Password</label>
							<input type="password" class="form-control" name="password"/>
						</div>
						<input type="submit" class="btn btn-primary mt-3" value="Login">
					</form>
			</div>
		</div>
	</div>
</body>
</html>