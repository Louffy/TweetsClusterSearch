<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<% java.util.Date d = new java.util.Date(); %>
<h1>
Today's date is <%= d.toString()  %> <font color = 'red'>zhangfeixue</font>zhang fei xue
</h1>


<form action="/example/html/form_action.asp" method="get">
  <p><input type="text" query="fquery" />
              <input type="submit" value="WeiSearch" /></p>
</form>


</body>
</html>