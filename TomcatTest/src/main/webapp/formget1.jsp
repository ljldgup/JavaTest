<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body >
	<h1>Using GET Method to Read Form Data</h1>
	<ul>
		<li><p>
				<b>First Name:</b>
				<%=request.getParameter("first_name")%>
			</p></li>
		<li><p>
				<b>Last Name:</b>
				<%=request.getParameter("last_name")%>
			</p></li>
	</ul>
</body>
</html>