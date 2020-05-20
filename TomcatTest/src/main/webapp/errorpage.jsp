<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Insert title here</title>
</head>
<body>
<body>
	<%
		// 设置错误代码，并说明原因    
		response.sendError(407, "Need authentication!!!");
	%>
</body>
</body>
</html>