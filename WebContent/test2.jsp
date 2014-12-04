<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>javaxp -- 关注java相关技术以及极限编程的程序员网站</title>
<script type="text/javascript" src="fckeditor.js"></script>   
</head>
<body>
<form action="${ctx }/user/groovyTest.action" method="post">
name:<input type="text" name="user.name" value="${user.name }" /></br>
age:<input type="text" name="user.age" value="${user.age }" />
<input type="submit" name='method:hello'  value="进入" ></input>
</form>
</body>
</html>