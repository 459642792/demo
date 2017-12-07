<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html lang="en">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<base href="<%=basePath%>">
	<link rel='stylesheet' href='http://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css'>
	<link rel='stylesheet' href='/welcome.css'>
</head>
<body>
<div class="container">
	<div class="row">
		<div class="col-md-offset-3 col-md-6">
			<form class="form-horizontal" action="/login/welcome" method="post">
				<span class="heading">用户登录</span>
				<div class="form-group">
					<input type="text" class="form-control" name="username" id="username" placeholder="用户名或电子邮件">
					<i class="fa fa-user"></i>
				</div>
				<div class="form-group help">
					<input type="password" class="form-control" name="password" id="password" placeholder="密　码">
					<i class="fa fa-lock"></i>
					<a href="#" class="fa fa-question-circle"></a>
				</div>
				<div class="form-group">
					<div class="main-checkbox">
						<input type="checkbox" value="None" id="checkbox1" name="check"/>
						<label for="checkbox1"></label>
					</div>
					<span class="text">Remember me</span>
					<button onclick="demoSubmit()" class="btn btn-default">登录</button>
				</div>
			</form>
		</div>
	</div>
</div>
</body>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.5.2/jquery.min.js" type="text/javascript"></script>
<script type="text/javascript">
</script>
</html>
