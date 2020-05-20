<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8" import="java.io.*,java.util.*"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Insert title here</title>
<style type="text/css">
body {
	background-color: yellow;
	margin: 0 auto;
	display: table-cell;
	vertical-align: middle;
}
</style>
</head>
<body>
	<div id="request">
		<h2>HTTP Header Request Example</h2>
		<table  border="1" >
			<tr bgcolor="#949494">
				<th>Header Name</th>
				<th>Header Value(s)</th>
			</tr>
			<%
				Enumeration headerNames = request.getHeaderNames();
				while (headerNames.hasMoreElements()) {
					String paramName = (String) headerNames.nextElement();
					out.print("<tr><td>" + paramName + "</td>\n");
					String paramValue = request.getHeader(paramName);
					out.println("<td> " + paramValue + "</td></tr>\n");
				}
			%>
		</table>
	</div>
</body>
</html>