<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Report a Problem</title>
<link rel="stylesheet" href="problem.css">
<link
	href="https://fonts.googleapis.com/css2?family=Material+Icons&display=block"
	rel="stylesheet">

</head>
<body>
	<h1
		style="font-size: 18px; color: rgba(63, 66, 49, 1); text-align: center; padding-top: 50px; font-weight: lighter;">Village
		Connect</h1>
	<nav id="main-nav">
		<ul>
			<li><a href="home.html">Home</a></li>
                <li><a href="problems">Report a Problem</a></li>
                <li><a href="about.html">About</a></li>
                <li><a href="contact.html">Contact</a></li>
		</ul>
	</nav>

	<div class="text">
		<span>Report a Problem</span><br> <br> <span>Please
			fill out the form to address your problem.</span>
	</div>
	<div class="form-container">
		<div class="style-form">
			<form class="inline-form" action="problems" method="post">
				<input type="hidden" name="action" value="postProblem">
				<div class="form-left">
					<label for="firstname">First Name</label> <input type="text"
						id="firstname" name="firstname" required> <label
						for="lastname">Last Name</label> <input type="text" id="lastname"
						name="lastname" required> <label for="email">Email
						*</label> <input type="email" id="email" name="email" required> <label
						for="subject">Subject *</label> <input type="text" id="subject"
						name="subject" required>
				</div>
				<div class="form-right">
					<label for="description">Address your Issue...</label>
					<textarea id="description" name="description" rows="10"></textarea>
				</div>
				<div class="submit-container">
					<input type="submit" value="Submit">
				</div>
			</form>
		</div>
	</div>

	<div class="text2">
		<span>Community Issues</span><br> <br> <span>Take a
			look at the issues raised by other community members...</span>
	</div>

	<div class="my-list">
		<ul>
			<c:forEach var="problem" items="${problems}">
				<li>
					<p>
						<c:out value="${problem.firstname}" />
						<c:out value="${problem.lastname}" />
						<br><br>
						
					</p>
					
					<p style="font-size:16px">
					    <c:out value="${problem.subject}" />:
						<c:out value="${problem.description}" />
					</p>
                    
					<form action="problems" method="post"
						style="display: inline; vertical-align: middle;">
						<input type="hidden" name="problemId"
							value="<c:out value='${problem.id}'/>" /> <input type="hidden"
							name="action" value="upvote" />
						<button type="submit"
							style="background: none; border: none; cursor: pointer; vertical-align: middle;">
							<span class="material-symbols-outlined">thumb_up</span>
						</button>
					</form> <span style="vertical-align: middle;">&nbsp;<c:out
							value="${problem.upvotes}" /></span>

				</li>
			</c:forEach>
		</ul>
	</div>

	<!-- Pagination Navigation -->
	<div class="pagination">
		<c:if test="${currentPage > 1}">
			<a href="problems?page=${currentPage - 1}" class="pagination-link">&laquo;
				Previous</a>
		</c:if>
		<c:forEach var="i" begin="1" end="${totalPages}">
			<a href="problems?page=${i}"
				class="pagination-link ${currentPage == i ? 'active' : ''}">${i}</a>
		</c:forEach>
		<c:if test="${currentPage < totalPages}">
			<a href="problems?page=${currentPage + 1}" class="pagination-link">Next
				&raquo;</a>
		</c:if>
	</div>

</body>
</html>
