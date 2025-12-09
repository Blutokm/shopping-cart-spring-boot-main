<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h2>Đăng nhập hệ thống</h2>
    <form action="login" method="post">
        Tên đăng nhập: <input type="text" name="username" required /><br><br>
        Mật khẩu: <input type="password" name="password" required /><br><br>
        <input type="submit" value="Đăng nhập" />
    </form>
    <p style="color:red;">${error}</p>
</body>
</html>