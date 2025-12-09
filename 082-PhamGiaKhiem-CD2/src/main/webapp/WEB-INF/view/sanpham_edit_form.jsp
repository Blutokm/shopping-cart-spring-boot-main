<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Sửa sản phẩm</title>
</head>
<body>
	<h2>Sửa Sản Phẩm</h2>
    <form:form method="POST" action="${pageContext.request.contextPath}/editSaveSanPham" modelAttribute="sanpham">
        <form:hidden path="id"/>
        Giá: <form:input path="gia" /><br><br>
        Tên sản phẩm: <form:input path="tensp" /><br><br>
        Số lượng: <form:input path="soluong" /><br><br>
        <input type="submit" value="Cập nhật" />
    </form:form>
    <br>
    <a href="/viewSanPham">Quay lại danh sách</a>
</body>
</html>