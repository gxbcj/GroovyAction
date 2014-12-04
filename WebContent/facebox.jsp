<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>facebox</title>
<script src="${ctx}/script/jquery-1.4.js" type="text/javascript"></script>
<link href="${ctx}/facebox/facebox.css" media="screen" rel="stylesheet" type="text/css"/>
<script src="${ctx}/facebox/facebox.js" type="text/javascript"></script> 
<script>
$(document).ready(function(){
	$('a[rel*=facebox]').facebox();

	$('#test1').click(function(){
		alert(111);
	});
	var div = $.facebox.settings.faceboxHtml;
	$('#test0').after(div);
	$('#facebox').find('.content').empty()
	.append($('#info'));
	$('#facebox').find('.close').click(function(){
		$('#facebox').hide();
	});
	$('#facebox').show();
});
</script>
</head>

<body>
<table>
	<tr>
		<td>
		<div id='test0'>
		</div>
		<a href="${ctx}/img/girl.jpg" rel='facebox' >terminate</a>
		<a href="#info" rel="facebox">text</a>
		<input type='button' class='facebox' rel="facebox"  value='text' />
		<div id='info'>
		<input type="text" id='text1' name="text1" value="text"  />
		<input id='test1' type="button" name="hello" value="确定" />
		</div>
		</td>
	</tr>
</table>
</body>
</html>