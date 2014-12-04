<%@ page language="java" isErrorPage="true" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/struts-tags" prefix="s" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html>
<head>
    <title><fmt:message key="errorPage.title"/></title>
    <link rel="stylesheet" type="text/css" media="all" href="<c:url value='/styles/${appConfig["csstheme"]}/theme.css'/>" />
</head>

<body id="error">
    <div id="page">
        <div id="content" class="clearfix">
            <div id="main">
                <h1>请全选并复制以下内容，并将之发给开发人员，谢谢 :-)</h1><pre>
操作人:
请求内容：<script language="JavaScript">
<!--
	document.write(window.location);
//-->
</script>
<%=com.yuqiaotech.utils.RequestUtil.getParameterMap(request)
%>
异常详细信息：

                <% if (exception != null) { %>
                   <% exception.printStackTrace(new java.io.PrintWriter(out)); %>
                <% } else if (request.getAttribute("javax.servlet.error.exception") != null) { %>
                    <% ((Exception)request.getAttribute("javax.servlet.error.exception"))
                                           .printStackTrace(new java.io.PrintWriter(out)); %>
                <% }else{
                	%>
<s:property value="exceptionStack"/>
action错误：
<s:actionerror  theme="simple"/>
消息：
<s:actionmessage  theme="simple"/>
字段错误：
<s:fielderror theme="simple"/>
                	<%
                } %>
                </pre>
            </div>
        </div>
    </div>
</body>
</html>
