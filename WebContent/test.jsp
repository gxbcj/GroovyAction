<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@page import="com.gxb.java.module.User"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%><html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>javaxp -- 关注java相关技术以及极限编程的程序员网站</title>
<script type="text/javascript" src="fckeditor.js"></script>   
</head>
<body>
${message }_${user.name }_${user.age }
<%
	List<User> list = new ArrayList<User>();
	User user = new User();
	list.add(user);
	
	Map<String, User> map = new HashMap<String, User>();
	User user1 = new User();
	map.put("user1", user1);
	
	session.setAttribute("list",list);
	session.setAttribute("map", map);

%>
</body>
</html>