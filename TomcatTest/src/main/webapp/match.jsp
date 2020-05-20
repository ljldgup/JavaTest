<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8" import="java.io.*,java.util.*"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Insert title here</title>
</head>
<body style="text-align：center">
	<h2>Auto Refresh Header and click count Example</h2>
	
		<%     Integer hitsCount =        (Integer)application.getAttribute("hitCounter");     
	if( hitsCount ==null || hitsCount == 0 ){        
		/* 第一次访问 */        
		out.println("Welcome to my website!");        
		hitsCount = 1;     }
	else{        /* 返回访问值 */        
		out.println("Welcome back to my website!");        
	hitsCount += 1;     }     
	application.setAttribute("hitCounter", hitsCount); 
	%>
	
	<%
		// Set refresh, autoload time as 5 seconds    
		response.setIntHeader("Refresh", 5);
		// Get current time   
		Calendar calendar = new GregorianCalendar();
		String am_pm;
		int hour = calendar.get(Calendar.HOUR);
		int minute = calendar.get(Calendar.MINUTE);
		int second = calendar.get(Calendar.SECOND);
		if (calendar.get(Calendar.AM_PM) == 0)
			am_pm = "AM";
		else
			am_pm = "PM";
		String CT = hour + ":" + minute + ":" + second + " " + am_pm;
		out.println("Crrent Time: " + CT + "\n");
	%>
	

</body>
</html>