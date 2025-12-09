<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Thêm Sản Phẩm Mới</title>
</head>
<body>
	<h2>Thêm Sản Phẩm Mới</h2>
    <form:form method="POST" action="saveSanPham" modelAttribute="sanpham">
        Giá: <form:input path="gia" /><br><br>
        Tên sản phẩm: <form:input path="tensp" /><br><br>
        Số lượng: <form:input path="soluong" /><br><br>
        <input type="submit" value="Lưu" />
    </form:form>
    <br>
    <a href="/viewSanPham">Quay lại danh sách</a>
</body>
</html>
